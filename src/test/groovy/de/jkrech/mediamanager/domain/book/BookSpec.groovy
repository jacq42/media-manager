package de.jkrech.mediamanager.domain.book

import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import spock.lang.Ignore

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import spock.lang.Specification

/**
 * Tests the handling of the events
 */
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

    def "should fail"() {
        when:
        initializeBook()

        then:
        isbn != book.isbn
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

    def "the isbn can not be changed"() {
        given: "an initialized book"
        initializeBook()

        when: "a BookUpdated event was caught"
        book.updated(new BookUpdated(author, title, language))

        then: "the isbn is the same"
        isbn == book.isbn
    }

    private def initializeBook() {
        book.initialized(new BookInitialized(isbn))
    }
}
