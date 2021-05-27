package com.danisaavedra.cleanarchitecture.repository.book.rest

import com.danisaavedra.cleanarchitecture.repository.book.dto.BookDto
import com.danisaavedra.cleanarchitecture.service.book.model.Book
import com.danisaavedra.cleanarchitecture.service.port.book.SearchBooksExternalPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class BooksRestImpl : SearchBooksExternalPort {

    override fun findBooks(): Book? {
        val uri = "https://jsonplaceholder.typicode.com/todos/1"
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers["X-COM-PERSIST"] = "NO"
        headers["X-COM-LOCATION"] = "USA"
        val entity = HttpEntity<String>(headers)
        return restTemplate.exchange(uri, HttpMethod.GET, entity, BookDto::class.java).body?.toModel()
    }
}