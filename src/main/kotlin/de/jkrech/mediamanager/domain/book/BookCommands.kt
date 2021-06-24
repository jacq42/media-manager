package de.jkrech.mediamanager.domain.book

import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class InitializeBook(
    
    @TargetAggregateIdentifier
    val isbn: Isbn
)

data class UpdateBook(
    
    @TargetAggregateIdentifier
    val isbn: Isbn,
    val author: Author?,
    val title: Title?,
    val language: Language?
)

data class DeleteBook(
    
    @TargetAggregateIdentifier
    val isbn: Isbn
)