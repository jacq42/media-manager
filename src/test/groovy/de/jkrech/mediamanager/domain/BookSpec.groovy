package de.jkrech.mediamanager.domain

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import spock.lang.Specification

class BookSpec extends Specification {

    private Isbn isbn = isbn()
    private Author author = author()
    private Title title = title()
    private Language language = language()

    def "a book can be initialized"() {
        when: "a BookInitialized event was caught"
        Book book = initializeBook()

        then: "the isbn has been initialized"
        isbn == book.isbn
    }

    def "the values of a book can be updated"() {
        given: "an initialized book"
        Book book = initializeBook()

        when: "a BookUpdated event was caught"
        book.update(new BookUpdated(author, title, language))

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
        Book book = initializeBook()

        when: "a BookUpdated event was caught"
        book.update(new BookUpdated(author, title, language))

        then: "the isbn is the same"
        isbn == book.isbn
    }

    private def initializeBook() {
        new Book(isbn)
    }
}
