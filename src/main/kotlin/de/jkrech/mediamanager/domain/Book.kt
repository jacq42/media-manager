package de.jkrech.mediamanager.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

open class Book(val isbn: Isbn) {
  
    var author: Author? = null
    var title: Title? = null
    var language: Language? = null
    
    init {
        LOGGER.info("book initialized with {}", isbn)
    }
    
    fun update(bookUpdated: BookUpdated) {
        this.author = bookUpdated.author
        this.title = bookUpdated.title
        this.language = bookUpdated.language
        LOGGER.info("book updated with {} {} {}", author, title, language)
    }
    
    companion object {
        val LOGGER: Logger = getLogger(Book::class.java)
    }
}