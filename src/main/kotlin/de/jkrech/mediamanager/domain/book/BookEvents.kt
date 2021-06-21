package de.jkrech.mediamanager.domain.book

import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title

interface Event

interface BookEvent: Event

data class BookInitialized(val isbn: Isbn) : BookEvent

data class BookUpdated(val author: Author?, val title: Title?, val language: Language?): BookEvent

data class BookDeleted(val isbn: Isbn): BookEvent
