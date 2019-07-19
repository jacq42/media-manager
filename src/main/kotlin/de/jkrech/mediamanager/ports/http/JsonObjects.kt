package de.jkrech.mediamanager.ports.http

data class BookJson(
    val isbn: String,
    val author: String,
    val title: String,
    val language: String
)