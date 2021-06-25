package de.jkrech.mediamanager.ports.http

import de.jkrech.mediamanager.application.BookDto
import de.jkrech.mediamanager.application.GetBookDetails
import de.jkrech.mediamanager.application.GetBookList
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import de.jkrech.mediamanager.domain.book.InitializeBook
import de.jkrech.mediamanager.domain.book.InvalidIsbnException
import de.jkrech.mediamanager.domain.book.Isbn
import de.jkrech.mediamanager.domain.book.UpdateBook
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api("Book handling controller")
@RestController
@RequestMapping("/books")
class BookController constructor(
  @Autowired val commandGateway: CommandGateway,
  @Autowired val queryGateway: QueryGateway
) {

  @ApiOperation(value = "create book", response = String::class)
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "201", description = "OK - new book created"),
      ApiResponse(responseCode = "409", description = "invalid isbn or other error")
    ]
  )
  @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun create(@RequestBody bookJson: BookJson): ResponseEntity<String> {
    try {
      val isbn = Isbn(bookJson.isbn)
      val initializeBookCmd = InitializeBook(isbn)
      val result: Boolean = commandGateway.sendAndWait(initializeBookCmd)
      if (result) return ResponseEntity(isbn.isbn, CREATED)
    } catch (e: InvalidIsbnException) {
      LOGGER.error("Book is not created: ", e)
    }
    return ResponseEntity(CONFLICT)
  }

  @ApiOperation(value = "update book details", response = String::class)
  @ApiImplicitParam(
    name = "isbnAsString",
    required = true,
    example = "978-1-491-98636-3",
    value = "(formatted) string representation of isbn"
  )
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "200", description = "OK - book updated"),
      ApiResponse(responseCode = "404", description = "book with specified isbn not found"),
      ApiResponse(responseCode = "409", description = "invalid isbn or other error")
    ]
  )
  @PutMapping(path = ["{isbnAsString}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
  fun update(
    @PathVariable isbnAsString: String,
    @RequestBody bookJson: BookJson): ResponseEntity<String> {

    try {
      // TODO Mapper für: UpdateBook aus BookJson generieren
      val isbn = Isbn(isbnAsString)
      val author = bookJson.author?.let { Author(it) }
      val title = bookJson.title?.let { Title(it) }
      val language = bookJson.language?.let { Language.valueOf(it) }
      val updateBook = UpdateBook(isbn, author, title, language)
      val result: Boolean = commandGateway.sendAndWait(updateBook)
      if (result) return ResponseEntity(isbn.isbn, OK)
    } catch (e: InvalidIsbnException) {
      LOGGER.error("Could not update book", e)
      return ResponseEntity("$isbnAsString", CONFLICT)
    }
    return ResponseEntity("$isbnAsString", NOT_FOUND)
  }

  @ApiOperation(value = "get book details", notes = "details of a book by isbn", response = BookJson::class)
  @ApiImplicitParam(
    name = "isbnAsString",
    required = true,
    example = "978-1-491-98636-3",
    value = "(formatted) string representation of isbn"
  )
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "200", description = "OK"),
      ApiResponse(responseCode = "400", description = "invalid isbn or other error"),
      ApiResponse(responseCode = "404", description = "book with specified isbn not found")
    ]
  )
  @GetMapping(path = ["{isbnAsString}"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun get(@PathVariable isbnAsString: String): ResponseEntity<BookJson> {
    try {
      val isbn = Isbn(isbnAsString)
      val getBookDetails = GetBookDetails(isbn)
      val queryResponse = queryGateway.query("getBookDetails", getBookDetails, BookDto::class.java)
      val bookDto = queryResponse.get()
      return ResponseEntity(fromBookDto(bookDto), OK)
    } catch (e: InvalidIsbnException) {
      LOGGER.error("Invalid isbn {}", isbnAsString)
    } catch (e: NullPointerException) {
      LOGGER.error("Unknown isbn {}", isbnAsString)
      return ResponseEntity(NOT_FOUND)
    }
    return ResponseEntity(BAD_REQUEST)
  }

  @ApiOperation(value = "get a list of all books", response = List::class)
  @ApiResponses(
    value = [
      ApiResponse(responseCode = "501", description = "Not yet implemented")
    ]
  )
  @GetMapping
  fun getAll(): ResponseEntity<List<BookJson>> {
    try {
      val getBookList = GetBookList("")
      val queryResponse = queryGateway.query("getBooks", getBookList, ResponseTypes.multipleInstancesOf(BookDto::class.java))
      val listOfBooks: List<BookDto> = queryResponse.get() as List<BookDto>
      if (listOfBooks.isEmpty()) {
        return ResponseEntity(NO_CONTENT)
      }
      val listOfBooksJson = listOfBooks.map { bookDto -> fromBookDto(bookDto) }
      return ResponseEntity(listOfBooksJson, OK)
    } catch (e: NullPointerException) {
      LOGGER.error("No books found: ", e)
    }
    return ResponseEntity(BAD_REQUEST)
  }

  private fun fromBookDto(bookDto: BookDto): BookJson {
    return BookJson(bookDto.isbn, bookDto.author, bookDto.title, bookDto.language)
  }

  companion object {
    val LOGGER: Logger = LoggerFactory.getLogger(BookController::class.java)
  }
}
