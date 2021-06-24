package de.jkrech.mediamanager.domain.book

class Isbn(val isbn: String) {

  init {
    if (!(regexIsbn10.matches(isbn) || regexIsbn13.matches(isbn))) {
      // TODO Prüfziffern:
      // - https://www.buecher-wiki.de/index.php/BuecherWiki/ISBN
      // - https://de.wikipedia.org/wiki/Internationale_Standardbuchnummer
      throw InvalidIsbnException("invalid ISBN")
    }
  }

  fun asNumber(): String {
    return isbn.replace("-", "")
  }

  override fun toString(): String = isbn

  override fun equals(other: Any?): Boolean {
    if (other !is Isbn) {
      return false
    }
    return isbn == other.isbn
  }

  companion object {
    val regexIsbn10 = Regex("[0-9]{1}-[0-9]{5}-[0-9]{3}-[0-9]{1}")
    val regexIsbn13 = Regex("[0-9]{3}-[0-9]{1}-[0-9]{3}-[0-9]{5}-[0-9]{1}")
  }
}