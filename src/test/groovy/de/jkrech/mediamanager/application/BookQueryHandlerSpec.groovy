package de.jkrech.mediamanager.application

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import org.axonframework.modelling.command.Aggregate
import org.axonframework.modelling.command.Repository

import de.jkrech.mediamanager.application.BookDto
import de.jkrech.mediamanager.ports.http.BookJson
import de.jkrech.mediamanager.domain.book.Isbn

import spock.lang.Shared
import spock.lang.Specification

class BookQueryHandlerSpec extends Specification {

    private Isbn isbn = isbn()

    private BookReadRepository bookRepository = Mock(BookReadRepository)

    @Shared
    private BookReadService bookQueryHandler

    def setup() {
        bookQueryHandler = new BookReadService(bookRepository)
    }

    def "an existing book can be loaded from the repository"() {
        given: "a mocked repository"
        BookDto bookDto = new BookDto(isbn.isbn, "", "", "")
        1 * bookRepository.findByIsbn(_) >> bookDto

        when: "loading a book with an isbn"
        BookJson bookJson = bookQueryHandler.bookBy(isbn)

        then: "we get the book"
        bookJson.isbn == bookDto.isbn
    }
}
