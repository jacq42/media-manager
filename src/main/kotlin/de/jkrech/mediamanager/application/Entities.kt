package de.jkrech.mediamanager.application

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id

@Entity
@Table(name = "books")
open class BookDto () {
    
    @Id lateinit var isbn: String
    lateinit var author: String
    lateinit var title: String
    lateinit var language: String
    
    constructor(isbn: String, author: String, title: String, language: String): this() {
        this.isbn = isbn
        this.author = author
        this.title = title
        this.language = language
    }
}