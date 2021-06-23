package de.jkrech.mediamanager.ports.http

import de.jkrech.mediamanager.TestFactory
import de.jkrech.mediamanager.application.GetBookDetails
import de.jkrech.mediamanager.domain.book.Isbn
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class BookControllerSpec extends Specification {

  private static final String ISBN = isbn().isbn
  private static final String AUTHOR = author().name
  private static final String TITLE = title().name
  private static final String LANGUAGE = language().name()

  private CommandGateway commandGateway = Mock()
  private QueryGateway queryGateway = Mock()

  @Shared
  private BookController bookController

  def setup() {
    bookController = new BookController(commandGateway, queryGateway)
  }

  // -- POST

  def "a new book is initialized"() {
    given: "a command gateway"
    1 * commandGateway.sendAndWait({ cmd -> cmd.isbn == isbn() }) >> true

    and: "a json representation of a book"
    BookJson jsonBook = jsonBook()

    when: "post the json"
    ResponseEntity<String> response = bookController.create(jsonBook)

    then: "a cmd is created"
    response.status == CREATED
    response.body == ISBN
  }

  def "book can not be initialized"() {
    given: "a command gateway"
    1 * commandGateway.sendAndWait({ cmd -> cmd.isbn == isbn() }) >> false

    and: "a json representation of a book"
    BookJson jsonBook = jsonBook()

    when: "post the json"
    ResponseEntity<String> response = bookController.create(jsonBook)

    then: "the status is CONFLICT"
    response.status == CONFLICT
    response.body == null
  }

  def "returns CONFLICT if there is an exception"() {
    given: "a command gateway"
    0 * commandGateway.sendAndWait(_)

    and: "a json with an invalid isbn"
    BookJson jsonBook = jsonBook("invalid")

    when: "call the method"
    ResponseEntity<String> response = bookController.create(jsonBook)

    then: "the status is CONFLICT"
    response.status == CONFLICT
    response.body == null
  }

  // -- PUT

  def "a book can be updated"() {
    given: "a command gateway"
    1 * commandGateway.sendAndWait({ cmd -> cmd.isbn == isbn() && cmd.author == author() }) >> true

    and: "a json representation of a book"
    BookJson jsonBook = jsonBook()

    when: "put the json"
    ResponseEntity<String> response = bookController.update(ISBN, jsonBook)

    then: "a update cmd is created and successfully sent to command gateway"
    response.status == OK
    response.body == ISBN
  }

  def "cannot update with invalid isbn"() {
    given: "a command gateway"
    def invalidIsbn = "invalidIsbn"
    0 * commandGateway.sendAndWait(_ as Isbn) >> true

    and: "a json representation of a book"
    BookJson jsonBook = jsonBook()

    when: "put the json"
    ResponseEntity<String> response = bookController.update(invalidIsbn, jsonBook)

    then: "a update cmd is created and successfully sent to command gateway"
    response.status == CONFLICT
    response.body == invalidIsbn

  }

  def "cannot update with unknown isbn"() {
    given: "a command gateway"
    def unknownIsbn = "555-1-491-98636-3"
    1 * commandGateway.sendAndWait({ cmd -> cmd.isbn == new Isbn(unknownIsbn) }) >> false

    and: "a json representation of a book"
    BookJson jsonBook = jsonBook()

    when: "put the json"
    ResponseEntity<String> response = bookController.update(unknownIsbn, jsonBook)

    then: "a update cmd is created and successfully sent to command gateway"
    response.status == NOT_FOUND
    response.body == unknownIsbn
  }

  // -- GET

  def "returns BAD_REQUEST if isbn is invalid"() {
    when: "call GET with invalid isbn"
    ResponseEntity<BookJson> response = bookController.get("123")

    then: "the status is BAD_REQUEST"
    response.status == BAD_REQUEST
    response.body == null
  }

  def "returns a saved book"() {
    given: "a mocked query gateway"
    1 * queryGateway.query("getBookDetails", new GetBookDetails(isbn()), BookJson.class) >> new CompletableFuture<BookJson>(jsonBook(isbn().isbn))

    when: "get by isbn"
    ResponseEntity<BookJson> response = bookController.get(isbn().isbn)

    then: "the status is OK"
    response.status == OK
    response.body == jsonBook(isbn().isbn)
  }

  // -- private

  private BookJson jsonBook() {
    jsonBook(ISBN)
  }

  private BookJson jsonBook(String isbn) {
    new BookJson(isbn, AUTHOR, TITLE, LANGUAGE)
  }
}
