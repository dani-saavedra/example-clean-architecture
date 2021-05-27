package com.danisaavedra.cleanarchitecture.service.book.model

data class Book(
        val userID: Int,
        val id: Int,
        val title: String,
        val completed: Boolean
)
