package de.jkrech.mediamanager.domain

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title
import static org.junit.Assert.*

import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.mediamanager.TestFactory
import de.jkrech.mediamanager.application.BookCommandHandler
import de.jkrech.mediamanager.application.InitializeBook
import de.jkrech.mediamanager.application.UpdateBook
import spock.lang.Ignore
import spock.lang.Specification

class BookSpec extends Specification {

    private Book book = new Book()

    private Isbn isbn = isbn()
    private Author author = author()
    private Title title = title()
    private Language language = language()

    def "a book can be initialized"() {
        when: "initializing the book"
        initializeBook()

        then: "the book is initialized"
        isbn == book.isbn
    }

    def "a book can be updated"() {
        given: "an initialized book"
        initializeBook()

        when: "the book is updated"
        book.updated(new BookUpdated(author, title, language))

        then: "the values are updated"
        author == book.author
        title == book.title
        language == book.language
    }

    private def initializeBook() {
        book.initialized(new BookInitialized(isbn))
    }
}
