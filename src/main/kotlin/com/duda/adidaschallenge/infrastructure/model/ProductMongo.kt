package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "products")
data class ProductMongo(@Id var id: String? = UUID.randomUUID().toString(),
                        val name: String)

