package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Isbn
import org.axonframework.modelling.command.TargetAggregateIdentifier
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Title
import de.jkrech.mediamanager.domain.Language

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