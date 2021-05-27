package com.danisaavedra.cleanarchitecture.delegate

import com.danisaavedra.cleanarchitecture.service.api.SearchBook
import org.springframework.stereotype.Service

@Service
class BooksDelegate(private val searchBook: SearchBook) {

    fun searchBooks() {
        searchBook.getBooks()
    }
}