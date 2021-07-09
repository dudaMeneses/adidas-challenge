package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "stocks")
data class StockMongo(@Id var id: String?, val total: Int, val reserves: List<ReserveMongo>) {
    constructor(total: Int) : this(null, total, listOf())
}
