package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Book
import de.jkrech.mediamanager.domain.BookInitialized
import de.jkrech.mediamanager.domain.BookUpdated
import de.jkrech.mediamanager.domain.Isbn
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Aggregate
class BookAggregate() {
    
    @AggregateIdentifier
    lateinit var isbn: Isbn
    
    var book: Book? = null
    
    constructor(isbn: Isbn): this() {
        apply(BookInitialized(isbn))
        LOGGER.info("book initialized with {}", isbn)
    }
    
    fun update(updateBook: UpdateBook) {
        apply(BookUpdated(updateBook.author, updateBook.title, updateBook.language))
        LOGGER.info("book updated with {} {} {}", updateBook.author, updateBook.title, updateBook.language)
    }
    
    @EventSourcingHandler
    fun initialized(bookInitialized: BookInitialized) {
        this.isbn = bookInitialized.isbn
        this.book = Book(isbn)
    }
    
    @EventSourcingHandler
    fun updated(bookUpdated: BookUpdated) {
        book?.update(bookUpdated)
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(BookAggregate::class.java)
    }
}