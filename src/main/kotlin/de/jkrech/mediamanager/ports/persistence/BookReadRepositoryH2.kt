package de.jkrech.mediamanager.ports.persistence

import de.jkrech.mediamanager.application.BookDto
import de.jkrech.mediamanager.application.BookReadRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.lang.Nullable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookReadRepositoryH2 : BookReadRepository {

  @Nullable
  override fun findByIsbn(isbn: String): Optional<BookDto> {
    LOGGER.info("get from db with isbn {}", isbn)
    return findById(isbn)
  }

  companion object {
    val LOGGER: Logger = LoggerFactory.getLogger(BookReadRepositoryH2::class.java)
  }
}