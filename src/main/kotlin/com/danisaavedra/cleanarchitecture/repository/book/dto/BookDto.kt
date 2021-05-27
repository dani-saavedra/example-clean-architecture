package com.danisaavedra.cleanarchitecture.repository.book.dto

import com.danisaavedra.cleanarchitecture.service.book.model.Book

data class BookDto(
        val userID: Int,
        val id: Int,
        val title: String,
        val completed: Boolean
) {
    fun toModel() = Book(
            userID = userID,
            id = id,
            title = title,
            completed = completed
    )
}
