package com.duda.adidaschallenge.infrastructure.database.mongo

import com.duda.adidaschallenge.infrastructure.model.ProductMongo
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ProductMongoDBRepository: ReactiveCrudRepository<ProductMongo, String>