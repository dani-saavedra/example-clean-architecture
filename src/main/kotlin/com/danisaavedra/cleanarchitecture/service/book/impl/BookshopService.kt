package com.danisaavedra.cleanarchitecture.service.book.impl

import com.danisaavedra.cleanarchitecture.dto.SaleInformationDto

class BookshopService {

    fun sellBook(data: SaleInformationDto) {
        if (data.saleMethod == "FISICO") {

        } else if (data.saleMethod == "CONTRA_ENTREGA") {

        } else if (data.saleMethod == "VIRTUAL") {

        }
    }
}
