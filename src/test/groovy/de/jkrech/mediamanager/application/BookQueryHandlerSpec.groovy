package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.TestFactory
import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.book.BookInitialized

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import org.axonframework.modelling.command.Aggregate
import org.axonframework.modelling.command.Repository

import de.jkrech.mediamanager.application.BookDto
import de.jkrech.mediamanager.ports.http.BookJson
import de.jkrech.mediamanager.domain.book.Isbn

import spock.lang.Shared
import spock.lang.Specification

class BookQueryHandlerSpec extends Specification {

  private BookReadRepository bookRepository = Mock(BookReadRepository)

  @Shared
  private BookReadService bookQueryHandler

  def setup() {
    bookQueryHandler = new BookReadService(bookRepository)
  }

  def "a book is persisted to the repository"() {
    when: "trigger the BookInitialized event"
    def bookInitialized = new BookInitialized(isbn())
    bookQueryHandler.bookInitialized(bookInitialized)

    then: "all data are persisted"
    1 * bookRepository.save({bookDto -> bookDto.isbn == isbn().isbn})
  }

  def "an existing book can be loaded from the repository"() {
    given: "a mocked repository"
    BookDto bookDto = new BookDto(isbn().isbn, author().name, title().name, language().name())
    1 * bookRepository.findByIsbn(_) >> bookDto

    when: "loading a book with an isbn"
    BookJson bookJson = bookQueryHandler.bookBy(isbn())

    then: "we get all details of the book"
    bookJson.isbn == bookDto.isbn
    bookJson.author == bookDto.author
    bookJson.title == bookDto.title
    bookJson.language == bookDto.language
  }

}
