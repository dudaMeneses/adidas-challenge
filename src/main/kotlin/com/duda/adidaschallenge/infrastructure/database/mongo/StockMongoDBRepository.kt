package com.duda.adidaschallenge.infrastructure.database.mongo

import com.duda.adidaschallenge.infrastructure.model.StockMongo
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface StockMongoDBRepository: ReactiveCrudRepository<StockMongo, String>{
    fun findByProductId(productId: String): Mono<StockMongo>
}