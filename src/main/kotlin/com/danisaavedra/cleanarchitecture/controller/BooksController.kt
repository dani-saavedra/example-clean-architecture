package com.danisaavedra.cleanarchitecture.controller

import com.danisaavedra.cleanarchitecture.delegate.BooksDelegate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ping")
class BooksController(private val booksDelegate: BooksDelegate) {

    @GetMapping
    fun ping(): ResponseEntity<Nothing> {
        booksDelegate.searchBooks()
        return ResponseEntity.ok().build()
    }
}