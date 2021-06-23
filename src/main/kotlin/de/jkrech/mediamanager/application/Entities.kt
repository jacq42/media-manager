package de.jkrech.mediamanager.application

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "books")
open class BookDto(@Id val isbn: String) {

  lateinit var author: String
  lateinit var title: String
  lateinit var language: String

  constructor(isbn: String, author: String, title: String, language: String) : this(isbn) {
    this.author = author
    this.title = title
    this.language = language
  }
}