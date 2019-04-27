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

class BookEventsSpec extends Specification {

    private FixtureConfiguration<Book> fixture;

    private Isbn isbn = isbn()
    private Author author = author()
    private Title title = title()
    private Language language = language()

    def setup() {
        fixture = new AggregateTestFixture<>(Book.class)
        def commandHandler = new BookCommandHandler(fixture.getRepository())
        fixture.registerAnnotatedCommandHandler(commandHandler)
    }

    def "sends an event on creation"() {
        when: "a book was initialized"
        def fixtureGivenWhen = fixture.given().when(new InitializeBook(isbn))

        then: "we get a BookInitialized event"
        fixtureGivenWhen
            .expectSuccessfulHandlerExecution()
            .expectEvents(new BookInitialized(isbn))
    }

    def "sends an event on update"() {
        given: "an initialized book"
        def fixtureGiven = fixture.given(new BookInitialized(isbn))

        when: "the book was updated"
        def fixtureGivenWhen = fixtureGiven.when(new UpdateBook(isbn, author, title, language))

        then: "we get a BookUpdated event"
        fixtureGivenWhen
            .expectSuccessfulHandlerExecution()
            .expectEvents(new BookUpdated(author, title, language))
    }

    def "update without initialize should fail"() {
        when: "an uninitialized book was updated"
        def fixtureGivenWhen = fixture.given().when(new UpdateBook(isbn, author, title, language))

        then: "we get an AggregateNotFoundException"
        fixtureGivenWhen
            .expectNoEvents()
            .expectException(AggregateNotFoundException.class)
    }
}
