package de.jkrech.mediamanager.application

import de.jkrech.mediamanager.domain.book.BookInitialized
import de.jkrech.mediamanager.domain.book.BookUpdated
import org.junit.jupiter.api.BeforeAll
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoSettings

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title

import de.jkrech.mediamanager.ports.http.BookJson
import spock.lang.Shared
import spock.lang.Specification

class BookQueryHandlerSpec extends Specification {

  private BookReadRepository bookRepository = Mock(BookReadRepository)

  @Shared
  private BookReadService bookQueryHandler

  def setup() {
    bookQueryHandler = new BookReadService(bookRepository)
  }

  // -- create

  def "a book is persisted to the repository"() {
    when: "a BookInitialized event occurs"
    def bookInitialized = new BookInitialized(isbn())
    bookQueryHandler.bookInitialized(bookInitialized)

    then: "all data are persisted"
    1 * bookRepository.save({ bookDto -> bookDto.isbn == isbn().isbn })
  }

  def "an existing book can be loaded from the repository"() {
    given: "a mocked repository"
    BookDto bookDto = new BookDto(isbn().isbn, author().name, title().name, language().name())
    1 * bookRepository.findByIsbn(_) >> Optional.of(bookDto)

    when: "loading a book with an isbn"
    BookDto bookFromRepository = bookQueryHandler.bookBy(isbn()).get()

    then: "we get all details of the book"
    bookFromRepository == bookDto
  }

  // -- update

  def "an existing book can be updated"() {
    when: "a BookUpdate event occurs"
    def bookUpdated = new BookUpdated(isbn(), author(), title(), language())
    bookQueryHandler.bookUpdated(bookUpdated)

    then: "all data are updated"
    1 * bookRepository.save({
      bookDto ->
        bookDto.isbn == isbn().isbn
            && bookDto.author == author().name
            && bookDto.title == title().name
            && bookDto.language == language().name()
    })
  }

}
