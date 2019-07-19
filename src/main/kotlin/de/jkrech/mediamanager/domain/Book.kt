package de.jkrech.mediamanager.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Book() {
  
    lateinit var isbn: Isbn
    
    var author: Author? = null
    var title: Title? = null
    var language: Language? = null
    
    constructor(isbn: Isbn) : this() {
        this.isbn = isbn
        LOGGER.info("book initialized with {}", isbn)
    }
    
    fun update(bookUpdated: BookUpdated) {
        this.author = bookUpdated.author
        this.title = bookUpdated.title
        this.language = bookUpdated.language
        LOGGER.info("book updated with {} {} {}", author, title, language)
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(Book::class.java)
    }
}