package de.jkrech.mediamanager.application

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import org.axonframework.modelling.command.Aggregate
import org.axonframework.modelling.command.Repository

import de.jkrech.mediamanager.domain.Isbn
import spock.lang.Shared
import spock.lang.Specification

class BookCommandHandlerSpec extends Specification {

    private Isbn isbn = isbn()
    private InitializeBook initializeBookCommand = new InitializeBook(isbn)
    private UpdateBook updateBookCommand = new UpdateBook(isbn, author(), title(), language())

    private Repository<BookAggregate> bookRepository = Mock()

    @Shared
    private BookService bookCommandHandler

    def setup() {
        bookCommandHandler = new BookService(bookRepository)
    }

    def "a new aggregate can be loaded from the repository"() {
        given: "a mocked repository"
        1 * bookRepository.newInstance(_)

        expect: "a new book was created"
        bookCommandHandler.initializeBook(initializeBookCommand)
    }

    def "an existing aggregate can be loaded from the repository"() {
        given: "a mocked repository"
        0 * bookRepository.newInstance(_)
        1 * bookRepository.load(isbn.isbn) >> Mock(Aggregate)

        expect: "the book was updated"
        bookCommandHandler.updateBook(updateBookCommand)
    }
}
