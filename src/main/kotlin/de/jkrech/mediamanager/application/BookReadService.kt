package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.book.BookInitialized
import de.jkrech.mediamanager.domain.book.BookUpdated
import de.jkrech.mediamanager.domain.book.Isbn
import de.jkrech.mediamanager.ports.http.BookJson
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private val String.Companion.EMPTY: String get() = ""

/**
 * Contains the read model
 */
@Service
class BookReadService(@Autowired val bookReadRepository: BookReadRepository) {

  @QueryHandler
  fun bookBy(isbn: Isbn): BookJson {
    val bookDto = bookReadRepository.findByIsbn(isbn)
    return toBook(bookDto)
  }

  @EventHandler
  fun bookInitialized(bookInitialized: BookInitialized) {
    LOGGER.info("book initialized: {}", bookInitialized)
    val bookDto = BookDto(bookInitialized.isbn.isbn)
    bookReadRepository.save(bookDto)
  }

  @EventHandler
  fun bookUpdated(bookUpdated: BookUpdated) {
    LOGGER.info("book updated: {}", bookUpdated)
    val author = bookUpdated.author?.name ?: String.EMPTY
    val title = bookUpdated.title?.name ?: String.EMPTY
    val language = bookUpdated.language?.name ?: String.EMPTY
    val bookDto = BookDto(bookUpdated.isbn.isbn, author, title, language)
    bookReadRepository.save(bookDto)
  }

  fun toBook(bookDto: BookDto): BookJson {
    return BookJson(bookDto.isbn, bookDto.author, bookDto.title, bookDto.language)
  }

  companion object {
    val LOGGER: Logger = LoggerFactory.getLogger(BookReadService::class.java)
  }
}