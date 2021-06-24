package de.jkrech.mediamanager.application

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.lang.Nullable
import java.util.*

@NoRepositoryBean
interface BookReadRepository : JpaRepository<BookDto, String> {

  @Nullable
  fun findByIsbn(isbn: String): Optional<BookDto>

}