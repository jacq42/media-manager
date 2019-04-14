package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Isbn
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class InitializeBook(
    
    @TargetAggregateIdentifier
    val isbn: Isbn
)

data class UpdateBook(
    
    @TargetAggregateIdentifier
    val isbn: Isbn,
    val author: String?,
    val title: String?,
    val language: String?
)

data class DeleteBook(
    
    @TargetAggregateIdentifier
    val isbn: Isbn
)