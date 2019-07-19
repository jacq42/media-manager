package de.jkrech.mediamanager.ports.persistence.http

import org.springframework.http.HttpStatus.NOT_IMPLEMENTED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/books")
class BookController {
    
    @GetMapping
    fun getAll(): ResponseEntity<List<String>> {
        return ResponseEntity(NOT_IMPLEMENTED)
    }
    
    @PostMapping
    fun create(): ResponseEntity<String> {
        return ResponseEntity("create", NOT_IMPLEMENTED)
    }
    
    @PutMapping
    @RequestMapping("{isbn}")
    fun update(@PathVariable isbn: String): ResponseEntity<String> {
        return ResponseEntity("update " + isbn, NOT_IMPLEMENTED)
    }
}