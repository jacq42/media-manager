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
        when: "a BookInitialized event was caught"
        initializeBook()

        then: "the isbn has been initialized"
        isbn == book.isbn
    }

    def "the values of a book can be updated"() {
        given: "an initialized book"
        initializeBook()

        when: "a BookUpdated event was caught"
        book.updated(new BookUpdated(author, title, language))

        then: "the author has been updated"
        book.author != null
        author == book.author

        and: "the title has been updated"
        book.title != null
        title == book.title

        and: "the language has been updated"
        book.language != null
        language == book.language
    }

    private def initializeBook() {
        book.initialized(new BookInitialized(isbn))
    }
}
