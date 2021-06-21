package de.jkrech.mediamanager.ports.http

import com.fasterxml.jackson.annotation.JsonProperty

data class BookJson(
    @JsonProperty("isbn")  val isbn: String,
    @JsonProperty("author") val author: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("language") val language: String
)