package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
data class ProductMongo(val id: String? = null, val name:String)
