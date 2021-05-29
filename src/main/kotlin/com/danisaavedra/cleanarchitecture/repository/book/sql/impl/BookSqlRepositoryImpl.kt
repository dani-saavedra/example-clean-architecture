package com.danisaavedra.cleanarchitecture.repository.book.sql.impl

import com.danisaavedra.cleanarchitecture.repository.book.sql.BookRepository
import com.danisaavedra.cleanarchitecture.service.book.model.Book
import com.danisaavedra.cleanarchitecture.service.port.book.SearchBooksExternalPort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
@Qualifier("sqlBookRepository")
class BookSqlRepositoryImpl : SearchBooksExternalPort {
    override fun findBooks(): List<Book> {
        // bookRepository.findAll().map(BookOrm::toModel)
        return listOf<Book>()
    }
}
