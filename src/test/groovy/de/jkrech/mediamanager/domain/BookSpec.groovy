package de.jkrech.mediamanager.domain

import static org.junit.Assert.*

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.mediamanager.application.InitializeBook
import spock.lang.Ignore
import spock.lang.Specification

class BookSpec extends Specification {

    private FixtureConfiguration<Book> fixture;

    def setup() {
        fixture = new AggregateTestFixture<>(Book.class)
    }

    @Ignore("add the expected events")
    def "sends an event on creation"() {
        given: "an isbn"
        Isbn isbn = new Isbn("123")

        when: "a book is initialized"
        fixture.when(new InitializeBook(isbn))

        then: "we get the expected events"
        true == true
    }
}
