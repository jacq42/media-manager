package de.jkrech.mediamanager

import de.jkrech.mediamanager.domain.Author
import de.jkrech.mediamanager.domain.Isbn
import de.jkrech.mediamanager.domain.Language
import de.jkrech.mediamanager.domain.Title
import spock.lang.Specification

class TestFactory extends Specification {

    static Isbn isbn() {
        new Isbn("978-1-491-98636-3")
    }

    static Author author() {
        new Author("author name")
    }

    static Title title() {
        new Title("book title")
    }

    static Language language() {
        Language.DE
    }
}
