package de.jkrech.mediamanager

import static org.junit.Assert.*

import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.mediamanager.application.BookCommandHandler
import de.jkrech.mediamanager.application.InitializeBook
import de.jkrech.mediamanager.application.UpdateBook
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Book
import de.jkrech.mediamanager.domain.Isbn
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import spock.lang.Ignore
import spock.lang.Specification

class TestFactory extends Specification {

    static Isbn isbn() {
        new Isbn("123")
    }

    static Author author() {
        new Author("author name")
    }

    static Title title() {
        new Title("book title")
    }

    static Language language() {
        Language.DE
    }
}
