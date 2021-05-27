package com.danisaavedra.cleanarchitecture.service.book

import com.danisaavedra.cleanarchitecture.service.api.SearchBook
import com.danisaavedra.cleanarchitecture.service.port.book.SearchBooksExternalPort
import org.springframework.stereotype.Service

@Service
class SearchBookImpl(private val searchBooksExternalPort: SearchBooksExternalPort) : SearchBook {
    override fun getBooks() {
        val findBooks = searchBooksExternalPort.findBooks()
        println(findBooks)
    }
}