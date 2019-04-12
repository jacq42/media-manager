package de.jkrech.mediamanager.domain

data class BookInitialized(val isbn: Isbn)

data class BookUpdated(val author: String?, val title: String?, val language: Language?)

data class BookDeleted(val isbn: Isbn)