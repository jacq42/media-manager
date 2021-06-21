package de.jkrech.mediamanager.domain.book

import de.jkrech.mediamanager.application.UpdateBook
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.book.Isbn
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

@Aggregate
open class Book() {

    @AggregateIdentifier
    lateinit var isbn: Isbn

    var author: Author? = null
    var title: Title? = null
    var language: Language? = null

    constructor(isbn: Isbn) : this() {
        apply(BookInitialized(isbn))
        LOGGER.info("book initialized with {}", isbn)
    }

    fun update(updateBook: UpdateBook) {
        apply(BookUpdated(updateBook.author, updateBook.title, updateBook.language));
        LOGGER.info("book updated with {} {} {}", updateBook.author, updateBook.title, updateBook.language)
    }

    @EventSourcingHandler
    fun initialized(bookInitialized: BookInitialized) {
        this.isbn = bookInitialized.isbn
    }

    @EventSourcingHandler
    fun updated(bookUpdated: BookUpdated) {
        this.author = bookUpdated.author
        this.title = bookUpdated.title
        this.language = bookUpdated.language
        LOGGER.info("book updated with {} {} {}", author, title, language)
    }
    
    companion object {
        val LOGGER: Logger = getLogger(Book::class.java)
    }
}