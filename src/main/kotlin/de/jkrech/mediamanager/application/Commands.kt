package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Isbn

data class InitializeBook(val isbn: Isbn)

data class UpdateBook(val author: String?, val title: String?, val language: String?)

data class DeleteBook(val isbn: Isbn)