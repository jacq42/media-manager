package de.jkrech.mediamanager.application

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.mediamanager.application.BookWriteService
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Book
import de.jkrech.mediamanager.domain.BookInitialized
import de.jkrech.mediamanager.domain.BookUpdated
import de.jkrech.mediamanager.domain.Isbn
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import spock.lang.Specification

class BookEventsSpec extends Specification {

    private FixtureConfiguration<Book> fixture;

    private Isbn isbn = isbn()
    private Author author = author()
    private Title title = title()
    private Language language = language()

    def setup() {
        fixture = new AggregateTestFixture<>(BookAggregate.class)
        def commandHandler = new BookWriteService(fixture.getRepository())
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
