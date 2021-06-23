package de.jkrech.mediamanager.domain.book


import spock.lang.Specification
import spock.lang.Unroll

class IsbnSpec extends Specification {

  @Unroll
  def "'#isbn' is invalid"() {
    when: "creating a new isbn from string"
    new Isbn(isbn)

    then: "throws an exception"
    thrown(InvalidIsbnException.class)

    where:
    isbn << ["", "123"]
  }

  @Unroll
  def "'#isbn' is valid"() {
    when: "creating a new isbn from string"
    new Isbn(isbn)

    then: "does not throws an exception"
    notThrown(InvalidIsbnException.class)

    where:
    isbn << ["978-1-491-98636-3", "3-86631-007-2"]
  }

  def "objects with the same number are equal"() {
    given: "an isbn"
    Isbn isbn1 = new Isbn("978-1-491-98636-3")

    and: "another isbn"
    Isbn isbn2 = new Isbn("978-1-491-98636-3")

    expect:
    isbn1 == isbn2
  }

  def "unformatted number"() {
    given : "an isbn"
    Isbn isbn = new Isbn("978-1-491-98636-3")

    expect:
    isbn.asNumber() == "9781491986363"
  }
}
