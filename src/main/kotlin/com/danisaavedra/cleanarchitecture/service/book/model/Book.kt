package com.danisaavedra.cleanarchitecture.service.book.model

data class Book(
    val id: String,
    val title: String,
    val completed: Boolean,
    val origin: String,
)
