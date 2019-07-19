package de.jkrech.mediamanager.application

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.axonframework.modelling.command.Aggregate

@Service
class BookWriteService(@Autowired bookEventSourcingRepository: Repository<BookAggregate>) {
    
    val bookEventSourcingRepository: Repository<BookAggregate>
    
    init {
        this.bookEventSourcingRepository = bookEventSourcingRepository
    }
    
    @CommandHandler
    @Throws(Exception::class)
    fun initializeBook(initializeBook: InitializeBook): Boolean {
        try {
            bookEventSourcingRepository.newInstance({BookAggregate(initializeBook.isbn)})
            return true
        } catch (e: Exception) {
            return false
        }
    }

    @CommandHandler
    @Throws(Exception::class)
    fun updateBook(updateBook: UpdateBook) {
        bookEventSourcingRepository.load(updateBook.isbn.toString())
            .invoke { book -> book.update(updateBook) }
    }
}