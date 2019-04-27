package de.jkrech.mediamanager.domain

import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Aggregate
class Book() {
  
    @AggregateIdentifier
    lateinit var isbn: Isbn
    
    var author: Author? = null
    var title: Title? = null
    var language: Language? = null
    
    constructor(isbn: Isbn) : this() {
        apply(BookInitialized(isbn))
        LOGGER.info("book initialized with {}", isbn)
    }
    
    fun upate(author: Author?, title: Title?, language: Language?) {
        apply(BookUpdated(author, title, language));
        LOGGER.info("book updated with {} {} {}", author, title, language)
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
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(Book::class.java)
    }
}