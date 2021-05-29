package com.danisaavedra.cleanarchitecture.repository.book.sql.orm

import com.danisaavedra.cleanarchitecture.service.book.model.Book

data class BookOrm(
    val userId: Int,
    val id: String,
    val title: String,
    val completed: Boolean
) {
    fun toModel() = Book(
        id = id,
        completed = completed,
        title = title,
        origin = "SQL",
    )
}

