package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.application.BookWriteService
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import de.jkrech.mediamanager.domain.book.*
import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import spock.lang.Specification

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

/**
 * Test the handling of commands: Sending a command should create an event (or not if the command is invalid)
 */
class BookEventsSpec extends Specification {

    private FixtureConfiguration<Book> fixture

    def setup() {
        fixture = new AggregateTestFixture<>(Book.class)
        def commandHandler = new BookWriteService(fixture.getRepository())
        fixture.registerAnnotatedCommandHandler(commandHandler)
    }

    def "sends an event on creation"() {
        when: "a book was initialized"
        def fixtureGivenWhen = fixture.given().when(new InitializeBook(isbn()))

        then: "we get a BookInitialized event"
        fixtureGivenWhen
            .expectSuccessfulHandlerExecution()
            .expectEvents(new BookInitialized(isbn()))
    }

    def "sends an event on update"() {
        given: "an initialized book"
        def fixtureGiven = fixture.given(new BookInitialized(isbn()))

        when: "the book was updated"
        def fixtureGivenWhen = fixtureGiven.when(new UpdateBook(isbn(), author(), title(), language()))

        then: "we get a BookUpdated event"
        fixtureGivenWhen
            .expectSuccessfulHandlerExecution()
            .expectEvents(new BookUpdated(isbn(), author(), title(), language()))
    }

    def "update without initialize should fail"() {
        when: "an uninitialized book was updated"
        def fixtureGivenWhen = fixture.given().when(new UpdateBook(isbn(), author(), title(), language()))

        then: "we get an AggregateNotFoundException"
        fixtureGivenWhen
            .expectNoEvents()
            .expectException(AggregateNotFoundException.class)
    }
}
