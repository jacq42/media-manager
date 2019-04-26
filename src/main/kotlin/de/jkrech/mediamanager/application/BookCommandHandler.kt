package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.Book
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.axonframework.modelling.command.Aggregate

class BookCommandHandler(@Autowired bookRepository: Repository<Book>) {
    
    val bookRepository: Repository<Book>
    
    init {
        this.bookRepository = bookRepository
    }
    
    @CommandHandler
    @Throws(Exception::class)
    fun initializeBook(initializeBook: InitializeBook) {
        bookRepository.newInstance({Book(initializeBook.isbn)})
    }
    
    @CommandHandler
    @Throws(Exception::class)
    fun updateBook(updateBook: UpdateBook) {
        var book: Aggregate<Book> = bookRepository.load(updateBook.isbn.toString())
        book.invoke{ it.upate(updateBook.author, updateBook.title, updateBook.language)}
    }
}