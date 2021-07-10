package com.duda.adidaschallenge.infrastructure.database

import com.duda.adidaschallenge.infrastructure.database.mongo.ProductMongoDBRepository
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(private val productMongoDBRepository: ProductMongoDBRepository)