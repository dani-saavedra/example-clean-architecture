package com.danisaavedra.cleanarchitecture.repository.book

import java.lang.RuntimeException

class InvalidSaleException(message: String?) : RuntimeException(message)
