package de.jkrech.mediamanager.application

import org.axonframework.commandhandling.CommandHandler
import de.jkrech.mediamanager.domain.Book
import org.axonframework.modelling.command.Repository

class CommandHandler(bookRepository: Repository<Book>) {
    
    val bookRepository: Repository<Book>
    
    init {
        this.bookRepository = bookRepository
    }
    
    @CommandHandler
    @Throws(Exception::class)
    fun initialize(initializeBook: InitializeBook) {
        bookRepository.newInstance({Book(initializeBook.isbn)})
    }
}