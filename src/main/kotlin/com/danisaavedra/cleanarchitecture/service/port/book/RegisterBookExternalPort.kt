package com.danisaavedra.cleanarchitecture.service.port.book

import com.danisaavedra.cleanarchitecture.service.book.model.Book


interface RegisterBookExternalPort {

    fun registerNewBook(book: Book)
}
