package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.book.Book
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookWriteService(@Autowired bookEventSourcingRepository: Repository<Book>) {
    
    val bookEventSourcingRepository: Repository<Book> = bookEventSourcingRepository

    @CommandHandler
    @Throws(Exception::class)
    fun initializeBook(initializeBook: InitializeBook): Boolean {
        return try {
            bookEventSourcingRepository.newInstance { Book(initializeBook.isbn) }
            true
        } catch (e: Exception) {
            // TODO throw an exception?
            false
        }
    }

    @CommandHandler
    @Throws(Exception::class)
    fun updateBook(updateBook: UpdateBook): Boolean {
        return try {
            bookEventSourcingRepository.load(updateBook.isbn.isbn).execute { book -> book.update(updateBook) }
            true
        } catch (e: Exception) {
            false
        }
    }
}