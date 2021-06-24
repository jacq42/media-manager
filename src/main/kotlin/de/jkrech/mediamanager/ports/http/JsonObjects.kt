package de.jkrech.mediamanager.ports.http

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class BookJson(
  @JsonProperty("isbn", required = true) val isbn: String,
  @JsonProperty("author") var author: String? = null,
  @JsonProperty("title") var title: String? = null,
  @JsonProperty("language") var language: String? = null
)