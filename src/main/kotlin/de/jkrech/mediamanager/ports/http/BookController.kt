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
import de.jkrech.mediamanager.domain.InvalidIsbnException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.axonframework.queryhandling.QueryGateway

@RestController
@RequestMapping("/books")
class BookController @Autowired constructor(
    val commandGateway: CommandGateway) {
    
    @PostMapping
    fun create(@RequestBody bookJson: BookJson): ResponseEntity<String> {
        try {
            val isbn = Isbn(bookJson.isbn)
            val initializeBookCmd = InitializeBook(isbn)
            val result: Boolean = commandGateway.sendAndWait(initializeBookCmd)
            if (result) return ResponseEntity(isbn.isbn, CREATED)
        } catch(e: InvalidIsbnException) {
            LOGGER.error("Book is not created: ", e)
        }
        return ResponseEntity(CONFLICT)
    }
    
    @PutMapping("{isbn}")
    fun update(@PathVariable isbn: String): ResponseEntity<String> {
        return ResponseEntity("update " + isbn, NOT_IMPLEMENTED)
    }
    
    @GetMapping("{isbn}")
    fun get(@PathVariable isbn: String): ResponseEntity<BookJson> {
        // TODO klären: readService direkt oder QueryBus?
        return ResponseEntity(BookJson("isbn", "", "", ""), HttpStatus.OK)
    }
    
    @GetMapping
    fun getAll(): ResponseEntity<List<String>> {
        return ResponseEntity(NOT_IMPLEMENTED)
    }
    
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(BookController::class.java)
    }
}