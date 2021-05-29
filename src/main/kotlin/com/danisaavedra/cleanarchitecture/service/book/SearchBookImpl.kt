package com.danisaavedra.cleanarchitecture.service.book

import com.danisaavedra.cleanarchitecture.service.api.SearchBook
import com.danisaavedra.cleanarchitecture.service.port.book.SearchBooksExternalPort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class SearchBookImpl(
    @Qualifier("restBookRepository") private val restBookRepository: SearchBooksExternalPort
) : SearchBook {
    override fun getBooks() {
        val findBooks = restBookRepository.findBooks()
        println(findBooks)
    }
}
