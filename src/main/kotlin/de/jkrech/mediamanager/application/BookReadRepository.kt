package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Isbn
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import org.springframework.lang.Nullable

@NoRepositoryBean
interface BookReadRepository : Repository<BookDto, Isbn> {
    
    @Nullable
    fun findByIsbn(isbn: Isbn): BookDto
    
    fun save(book: BookDto): BookDto
}