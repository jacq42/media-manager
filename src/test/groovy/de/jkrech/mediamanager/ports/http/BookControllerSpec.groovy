package de.jkrech.mediamanager.ports.http

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.CREATED

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import spock.lang.Shared
import spock.lang.Specification

class BookControllerSpec extends Specification {

    private static final String ISBN = isbn().isbn
    private static final String AUTHOR = author().name
    private static final String TITLE = title().name
    private static final String LANGUAGE = language().name()

    private CommandGateway commandGateway = Mock()

    @Shared
    private BookController bookController

    def setup() {
        bookController = new BookController(commandGateway)
    }

    // -- POST

    def "a new book is initialized"() {
        given: "a command gateway"
        1 * commandGateway.sendAndWait({cmd -> cmd.isbn == isbn()}) >> true

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
        1 * commandGateway.sendAndWait({cmd -> cmd.isbn == isbn()}) >> false

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

    // -- GET

    def "returns BAD_REQUEST if isbn is invalid"() {
        when: "call GET with invalid isbn"
        ResponseEntity<BookJson> response = bookController.get("123")

        then: "the status is BAD_REQUEST"
        response.status == BAD_REQUEST
        response.body == null
    }

    // -- private

    private BookJson jsonBook() {
        jsonBook(ISBN)
    }

    private BookJson jsonBook(String isbn) {
        new BookJson(isbn, AUTHOR, TITLE, LANGUAGE)
    }
}
