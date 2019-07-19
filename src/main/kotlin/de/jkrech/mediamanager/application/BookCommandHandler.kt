package de.jkrech.mediamanager.application

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.Repository
import org.springframework.beans.factory.annotation.Autowired

class BookCommandHandler(@Autowired bookRepository: Repository<BookAggregate>) {
    
    val bookRepository: Repository<BookAggregate>
    
    init {
        this.bookRepository = bookRepository
    }
    
    @CommandHandler
    @Throws(Exception::class)
    fun initializeBook(initializeBook: InitializeBook) {
        bookRepository.newInstance({BookAggregate(initializeBook.isbn)})
    }
    
    @CommandHandler
    @Throws(Exception::class)
    fun updateBook(updateBook: UpdateBook) {
        bookRepository.load(updateBook.isbn.toString())
            .invoke{ book -> book.update(updateBook)}
    }
}