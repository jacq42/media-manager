package de.jkrech.mediamanager.domain

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateRoot
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.eventsourcing.EventSourcingHandler

@AggregateRoot
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
    
    @EventSourcingHandler
    fun initialize(bookInitialized: BookInitialized) {
        this.isbn = bookInitialized.isbn
    }
}