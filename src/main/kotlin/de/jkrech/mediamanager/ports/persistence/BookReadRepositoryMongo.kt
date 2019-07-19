package de.jkrech.mediamanager.ports.persistence

//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Profile
//import org.springframework.data.mongodb.core.MongoOperations
//import org.springframework.lang.Nullable
//import org.springframework.stereotype.Repository
//
//import de.jkrech.mediamanager.application.BookReadRepository
//import de.jkrech.mediamanager.domain.Book
//import de.jkrech.mediamanager.domain.Isbn

//@Repository
//@Profile("mongodb")
//class BookReadRepositoryMongo(@Autowired val operations: MongoOperations) : BookReadRepository {
//    
//    @Nullable
//    override fun findByIsbn(isbn: Isbn): Book {
//        return Book(isbn)
//    }
//    
//    override fun save(book: Book): Book {
//        val author: String = book.author?.name ?: ""
//        val title: String = book.title?.name ?: ""
//        val language: String = book.language?.name ?: ""
//        val bookDto = BookDto(book.isbn.isbn, author, title, language)
//        
//        operations.save(bookDto)
//        return book
//    }
//}