package com.danisaavedra.cleanarchitecture.repository.book.sql.orm

import com.danisaavedra.cleanarchitecture.service.book.model.Book
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "book")
data class BookOrm(
    @Id
    val id: Int,
    @Column
    val title: String,

    @Column
    val completed: Boolean
) {
    fun toModel() = Book(
        id = id.toString(),
        completed = completed,
        title = title,
        origin = "SQL",
    )

    companion object {
        fun fromModel(model: Book) = BookOrm(
            id = model.id.toInt(),
            title = model.title,
            completed = model.completed,
        )
    }
}

