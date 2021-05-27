package com.danisaavedra.cleanarchitecture.service.port.book

import com.danisaavedra.cleanarchitecture.repository.book.dto.BookDto
import com.danisaavedra.cleanarchitecture.service.book.model.Book

interface SearchBooksExternalPort {

    fun findBooks(): Book?
}