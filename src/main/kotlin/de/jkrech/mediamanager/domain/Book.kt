package de.jkrech.mediamanager.domain

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateRoot

@AggregateRoot
@Aggregate
class Book(isbn: Isbn) {
  
    @AggregateIdentifier
    val isbn: Isbn
    var author: Author? = null
    var title: Title? = null
    var language: Language? = null
    
    init {
        this.isbn = isbn
    }
}