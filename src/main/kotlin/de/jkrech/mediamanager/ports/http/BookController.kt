package de.jkrech.mediamanager.ports.http

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_IMPLEMENTED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import de.jkrech.mediamanager.application.BookAggregate
import de.jkrech.mediamanager.application.InitializeBook
import de.jkrech.mediamanager.domain.Isbn

@RestController
@RequestMapping("/books")
class BookController @Autowired constructor(
    val commandGateway: CommandGateway) {
    
    @PostMapping
    fun create(@RequestBody bookJson: BookJson): ResponseEntity<String> {
        val isbn = Isbn(bookJson.isbn)
        val initializeBookCmd = InitializeBook(isbn)
        val result: Boolean = commandGateway.sendAndWait(initializeBookCmd)
        return if (result) ResponseEntity(isbn.isbn, CREATED) else ResponseEntity(CONFLICT)
    }
    
    @PutMapping
    @RequestMapping("{isbn}")
    fun update(@PathVariable isbn: String): ResponseEntity<String> {
        return ResponseEntity("update " + isbn, NOT_IMPLEMENTED)
    }
    
    @GetMapping
    fun getAll(): ResponseEntity<List<String>> {
        return ResponseEntity(NOT_IMPLEMENTED)
    }
}