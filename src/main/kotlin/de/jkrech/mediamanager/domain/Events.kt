package de.jkrech.mediamanager.domain

interface BookEvent

data class BookInitialized(val isbn: Isbn) : BookEvent

data class BookUpdated(val author: String?, val title: String?, val language: Language?): BookEvent

data class BookDeleted(val isbn: Isbn): BookEvent