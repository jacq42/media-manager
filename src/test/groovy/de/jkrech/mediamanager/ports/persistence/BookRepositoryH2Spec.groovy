package de.jkrech.mediamanager.ports.persistence

import static de.jkrech.mediamanager.TestFactory.author
import static de.jkrech.mediamanager.TestFactory.isbn
import static de.jkrech.mediamanager.TestFactory.language
import static de.jkrech.mediamanager.TestFactory.title
import de.jkrech.mediamanager.application.BookDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification

@DataJpaTest
class BookRepositoryH2Spec extends Specification {

  @Autowired
  private TestEntityManager entityManager

  @Autowired
  private BookReadRepositoryH2 bookReadRepositoryH2

  def "can save a book and find by isbn"() {
    given: "a book to save"
    def bookDto = new BookDto(isbn().asNumber(), author().name, title().name, language().name())

    when: "saving"
    entityManager.persist(bookDto)
    entityManager.flush()

    then: "it can be found"
    bookReadRepositoryH2.findByIsbn(isbn().asNumber()).get() == bookDto
  }
}
