package de.jkrech.mediamanager.domain

data class Isbn(val isbn: String) {
    override fun toString(): String = isbn
}

data class Author(val name: String)

data class Title(val name: String)