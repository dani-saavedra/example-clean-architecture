package com.danisaavedra.cleanarchitecture.repository.book.rest

import com.beust.klaxon.Klaxon
import com.danisaavedra.cleanarchitecture.repository.book.rest.dto.BookDto
import com.danisaavedra.cleanarchitecture.service.book.model.Book
import com.danisaavedra.cleanarchitecture.service.port.book.SearchBooksExternalPort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
@Qualifier("restBookRepository")
class BooksRestImpl : SearchBooksExternalPort {

    override fun findBooks(): List<Book> {
        val uri = "https://jsonplaceholder.typicode.com/todos/"
        val body = RestTemplate().getForEntity(uri, String::class.java).body
        return Klaxon().parseArray<BookDto>(body!!)?.map { it -> it.toModel() } ?: listOf()
    }
}
