package de.jkrech.mediamanager.ports.persistence

import de.jkrech.mediamanager.application.BookDto
import de.jkrech.mediamanager.application.BookReadRepository
import de.jkrech.mediamanager.domain.Isbn
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class BookReadRepositoryH2 : BookReadRepository {
    
    override fun findByIsbn(isbn: Isbn): BookDto {
        LOGGER.info("get from db")
        return BookDto(isbn.isbn, "", "", "")
    }
    
    override fun save(book: BookDto): BookDto {
        LOGGER.info("save to db")
        return book
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(BookReadRepositoryH2::class.java)
    }
}