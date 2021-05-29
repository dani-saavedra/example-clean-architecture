package com.danisaavedra.cleanarchitecture.repository.book.sql.impl

import com.danisaavedra.cleanarchitecture.repository.book.sql.BookRepository
import com.danisaavedra.cleanarchitecture.service.book.model.Book
import com.danisaavedra.cleanarchitecture.service.port.book.SearchBooksExternalPort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
@Qualifier("sqlBookRepository")
class BookSqlRepositoryImpl(private val bookRepository: BookRepository) : SearchBooksExternalPort {
    override fun findBooks(): List<Book> {
        return bookRepository.findAll().map { it.toModel() }
    }
}
