package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Book
import de.jkrech.mediamanager.domain.BookInitialized
import org.axonframework.eventhandling.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import de.jkrech.mediamanager.domain.Isbn
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.ports.http.BookJson
import org.springframework.beans.factory.annotation.Autowired

@Service
class BookReadService(@Autowired val bookReadRepository: BookReadRepository) {
    
    fun bookBy(isbn: Isbn): BookJson {
        val bookDto = bookReadRepository.findByIsbn(isbn)
        return toBook(bookDto)
    }
    
    @EventHandler
    fun bookInitialized(bookInitialized: BookInitialized) {
        LOGGER.info("book initialized: " + bookInitialized)
        val bookDto = BookDto(bookInitialized.isbn.isbn, "", "", "")
        bookReadRepository.save(bookDto)
    }
    
    fun toBook(bookDto: BookDto): BookJson {
        return BookJson(bookDto.isbn, bookDto.author, bookDto.title, bookDto.language)
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(BookReadService::class.java)
    }
}