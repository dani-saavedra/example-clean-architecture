package com.danisaavedra.cleanarchitecture.dto

data class SaleInformationDto(
    val saleMethod: String,
    val booksSold: List<BookDto>
) {
    data class BookDto(
        val id:String,
        val numberBooks: Int,
        val price: Int
    )
}
