package de.jkrech.mediamanager.ports.http

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class BookJson(

  @ApiModelProperty(name = "isbn", value = "ISBN", example = "978-1-491-98636-3")
  @JsonProperty("isbn", required = true)
  val isbn: String,

  @ApiModelProperty(name = "author", value = "Author(s) as string", example = "Eric Evans", allowEmptyValue = true)
  @JsonProperty("author")
  var author: String? = null,

  @ApiModelProperty(name = "title", value = "book title", example = "Domain-Driven Design", allowEmptyValue = true)
  @JsonProperty("title")
  var title: String? = null,

  @ApiModelProperty(name = "language", value = "book language", example = "DE", allowEmptyValue = true)
  @JsonProperty("language")
  var language: String? = null
)