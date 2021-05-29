package com.danisaavedra.cleanarchitecture.service.port.book

import com.danisaavedra.cleanarchitecture.service.book.model.Book

interface SearchBooksExternalPort {

    fun findBooks(): List<Book>
}