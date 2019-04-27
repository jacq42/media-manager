package de.jkrech.mediamanager.domain

import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Book() {
  
    @AggregateIdentifier
    lateinit var isbn: Isbn
    
    var author: Author? = null
    var title: Title? = null
    var language: Language? = null
    
    constructor(isbn: Isbn) : this() {
        apply(BookInitialized(isbn))
    }
    
    fun upate(author: Author?, title: Title?, language: Language?) {
        apply(BookUpdated(author, title, language));
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
}