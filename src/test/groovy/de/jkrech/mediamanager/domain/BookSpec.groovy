package de.jkrech.mediamanager.domain

import static org.junit.Assert.*

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.mediamanager.application.BookCommandHandler
import de.jkrech.mediamanager.application.InitializeBook
import de.jkrech.mediamanager.application.UpdateBook
import spock.lang.Ignore
import spock.lang.Specification

class BookSpec extends Specification {

    private FixtureConfiguration<Book> fixture;

    def setup() {
        fixture = new AggregateTestFixture<>(Book.class)
        def commandHandler = new BookCommandHandler(fixture.getRepository())
        fixture.registerAnnotatedCommandHandler(commandHandler)
    }

    def "sends an event on creation"() {
        given: "an isbn"
        def isbn = new Isbn("123")

        when: "a book is initialized"
        def fixtureGivenWhen = fixture.given().when(new InitializeBook(isbn))

        then: "we get the expected events"
        fixtureGivenWhen.expectEvents(new BookInitialized(isbn))
    }

    def "sends an event on update"() {
        given: "an isbn"
        def isbn = new Isbn("123")
        def author = author()
        def title = title()
        def language = Language.DE

        and: "a book"
        def fixtureGiven = fixture.given(new BookInitialized(isbn))

        when: "the book is updated"
        def fixtureGivenWhen = fixtureGiven.when(new UpdateBook(isbn, author, title, language))

        then: "we get the expected events"
        fixtureGivenWhen.expectEvents(new BookUpdated(author, title, language))
    }

    def author() {
        new Author("author name")
    }

    def title() {
        new Title("book title")
    }
}
