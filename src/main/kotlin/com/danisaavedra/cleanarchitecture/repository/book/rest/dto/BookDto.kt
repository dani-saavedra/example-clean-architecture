package com.danisaavedra.cleanarchitecture.repository.book.rest.dto

import com.danisaavedra.cleanarchitecture.service.book.model.Book

data class BookDto(
    val id: Int,
    val title: String,
    val completed: Boolean
) {
    fun toModel() = Book(
        id = id.toString(),
        title = title,
        completed = completed,
        origin = "REST"
    )
}
