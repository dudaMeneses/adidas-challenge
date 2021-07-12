package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "stocks")
data class StockMongo(@Id var id: String? = UUID.randomUUID().toString(),
                      val productId: String,
                      var total: Int = 0)
