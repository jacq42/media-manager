package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.book.Isbn

data class GetBookDetails(
  val isbn: Isbn
)

data class GetBookList(
  val filter: String
)