package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.book.Book
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookWriteService(@Autowired val bookEventSourcingRepository: Repository<Book>) {

    @CommandHandler
    @Throws(Exception::class)
    fun initializeBook(initializeBook: InitializeBook): Boolean {
        return try {
            bookEventSourcingRepository.newInstance { Book(initializeBook.isbn) }
            true
        } catch (e: Exception) {
            throw e
        }
    }

    @CommandHandler
    @Throws(Exception::class)
    fun updateBook(updateBook: UpdateBook): Boolean {
        return try {
            bookEventSourcingRepository.load(updateBook.isbn.isbn).execute { book -> book.update(updateBook) }
            true
        } catch (e: Exception) {
            throw e
        }
    }
}