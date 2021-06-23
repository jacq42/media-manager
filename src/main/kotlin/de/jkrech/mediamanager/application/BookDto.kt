package de.jkrech.mediamanager.application

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "books")
data class BookDto(

  @Id
  @Column(name = "isbn", unique = true)
  val isbn: String,

  @Column(name = "author")
  val author: String,

  @Column(name = "title")
  val title: String,

  @Column(name = "language")
  val language: String
)
