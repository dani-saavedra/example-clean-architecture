package com.danisaavedra.cleanarchitecture.repository.book.sql

import com.danisaavedra.cleanarchitecture.repository.book.sql.orm.BookOrm
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<BookOrm,Int>
