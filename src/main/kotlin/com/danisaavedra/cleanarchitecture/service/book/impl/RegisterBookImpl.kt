package com.danisaavedra.cleanarchitecture.service.book.impl

import com.danisaavedra.cleanarchitecture.service.book.api.IRegisterBook
import com.danisaavedra.cleanarchitecture.service.book.model.Book
import com.danisaavedra.cleanarchitecture.service.port.book.RegisterBookExternalPort
import org.springframework.stereotype.Service

@Service
class RegisterBookImpl(
    private val registerBookExternalPort: RegisterBookExternalPort
) : IRegisterBook {

    override fun registerNewDummyBook() {
        registerBookExternalPort.registerNewBook(Book("1", "Clean architecture", true, ""))
    }
}
