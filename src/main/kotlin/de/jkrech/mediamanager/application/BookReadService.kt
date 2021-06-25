package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.book.BookInitialized
import de.jkrech.mediamanager.domain.book.BookUpdated
import de.jkrech.mediamanager.domain.book.Isbn
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

private val String.Companion.EMPTY: String get() = ""

/**
 * Contains the read model
 */
@Service
class BookReadService @Autowired constructor(val bookReadRepository: BookReadRepository) {

  @EventHandler
  fun bookInitialized(bookInitialized: BookInitialized) {
    LOGGER.info("book initialized: {}", bookInitialized)
    val bookDto = BookDto(bookInitialized.isbn.isbn, String.EMPTY, String.EMPTY, String.EMPTY)
    bookReadRepository.save(bookDto)
  }

  @EventHandler
  fun bookUpdated(bookUpdated: BookUpdated) {
    LOGGER.info("book updated: {}", bookUpdated)
    val authorOrEmpty = bookUpdated.author?.name ?: String.EMPTY
    val titleOrEmpty = bookUpdated.title?.name ?: String.EMPTY
    val languageOrEmpty = bookUpdated.language?.name ?: String.EMPTY
    val bookDto = BookDto(bookUpdated.isbn.isbn, authorOrEmpty, titleOrEmpty, languageOrEmpty)
    bookReadRepository.save(bookDto)
  }

  @QueryHandler(queryName = "getBookDetails")
  fun bookDetails(getBookDetails: GetBookDetails): Optional<BookDto> {
    return bookBy(getBookDetails.isbn)
  }

  @QueryHandler(queryName = "getBooks")
  fun bookList(getBookList: GetBookList): List<BookDto> {
    return bookReadRepository.findAll()
  }

  private fun bookBy(isbn: Isbn): Optional<BookDto> {
    return bookReadRepository.findByIsbn(isbn.isbn)
  }

  companion object {
    val LOGGER: Logger = LoggerFactory.getLogger(BookReadService::class.java)
  }
}