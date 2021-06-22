package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.book.Book

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import org.axonframework.modelling.command.Aggregate
import org.axonframework.modelling.command.Repository

import de.jkrech.mediamanager.domain.book.Isbn
import spock.lang.Shared
import spock.lang.Specification

class BookCommandHandlerSpec extends Specification {

    private Isbn isbn = isbn()

    private InitializeBook initializeBookCommand = new InitializeBook(isbn)
    private UpdateBook updateBookCommand = new UpdateBook(isbn, author(), title(), language())

    private Repository<Book> bookRepository = Mock()

    @Shared
    private BookWriteService bookWriteService

    def setup() {
        bookWriteService = new BookWriteService(bookRepository)
    }

    def "a new aggregate can be loaded from the repository"() {
        given: "a mocked repository"
        1 * bookRepository.newInstance(_)

        expect: "a new book was created"
        bookWriteService.initializeBook(initializeBookCommand)
    }

    def "an existing aggregate can be loaded from the repository"() {
        given: "a mocked repository"
        0 * bookRepository.newInstance(_)
        1 * bookRepository.load(isbn.isbn) >> Mock(Aggregate)

        expect: "the book was updated"
        bookWriteService.updateBook(updateBookCommand)
    }
}
