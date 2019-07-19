package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Book
import de.jkrech.mediamanager.domain.BookInitialized
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BookReadService(val bookReadRepository: BookReadRepository) {
    
    @EventHandler
    fun bookInitialized(bookInitialized: BookInitialized) {
        LOGGER.info("book initialized: " + bookInitialized)
        val bookDto = BookDto(bookInitialized.isbn.isbn, "", "", "")
        bookReadRepository.save(bookDto)
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(BookReadService::class.java)
    }
}