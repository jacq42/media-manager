package de.jkrech.mediamanager.domain

import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateIdentifier

@Aggregate
class Book(isbn: Isbn, author: Author?, title: Title?, language: Language?) {
  
  @AggregateIdentifier
  val isbn: Isbn
  var author: Author? = null
  var title: Title? = null
  var language: Language? = null
  
  init {
    this.isbn = isbn
    this.author = author
    this.title = title
    this.language = language
  }
}