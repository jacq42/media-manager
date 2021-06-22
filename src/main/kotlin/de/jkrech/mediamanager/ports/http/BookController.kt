package de.jkrech.mediamanager.ports.http

import de.jkrech.mediamanager.application.InitializeBook
import de.jkrech.mediamanager.application.UpdateBook
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import de.jkrech.mediamanager.domain.book.InvalidIsbnException
import de.jkrech.mediamanager.domain.book.Isbn
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.NOT_IMPLEMENTED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController @Autowired constructor(
    val commandGateway: CommandGateway) {
    
    @PostMapping
    fun create(@RequestBody bookJson: BookJson): ResponseEntity<String> {
        try {
            val isbn = Isbn(bookJson.isbn)
            val initializeBookCmd = InitializeBook(isbn)
            val result: Boolean = commandGateway.sendAndWait(initializeBookCmd)
            if (result) return ResponseEntity(isbn.isbn, CREATED)
        } catch(e: InvalidIsbnException) {
            LOGGER.error("Book is not created: ", e)
        }
        return ResponseEntity(CONFLICT)
    }
    
    @PutMapping(path = ["{isbnAsString}"])
    fun update(
        @PathVariable isbnAsString: String,
        @RequestBody bookJson: BookJson): ResponseEntity<String> {

        try {
            val isbn = Isbn(isbnAsString)
            var author = bookJson.author?.let { Author(it) }
            var title = bookJson.title?.let { Title(it) }
            var language = bookJson.language?.let { Language.valueOf(it) }
            val updateBook = UpdateBook(isbn, author, title, language)
            val result: Boolean = commandGateway.sendAndWait(updateBook)
            if (result) return ResponseEntity(isbn.isbn, OK)
        } catch( e: InvalidIsbnException) {
            LOGGER.error("Could not update book", e)
            return ResponseEntity("$isbnAsString", CONFLICT)
        }
        return ResponseEntity("$isbnAsString", NOT_FOUND)
    }
    
    @GetMapping("{isbnAsString}")
    fun get(@PathVariable isbnAsString: String): ResponseEntity<BookJson> {
        // TODO klären: readService direkt oder QueryBus? -> QueryBus, denn der readService lädt das Aggregate mit allen Events
        try {
          val isbn = Isbn(isbnAsString)
          return ResponseEntity(BookJson(isbn.isbn, "", "", ""), NOT_IMPLEMENTED)
        } catch (e: InvalidIsbnException) {
          LOGGER.error("Invalid isbn {}", isbnAsString)
        }
        return ResponseEntity(BAD_REQUEST)
    }
    
    @GetMapping
    fun getAll(): ResponseEntity<List<String>> {
        return ResponseEntity(NOT_IMPLEMENTED)
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(BookController::class.java)
    }
}