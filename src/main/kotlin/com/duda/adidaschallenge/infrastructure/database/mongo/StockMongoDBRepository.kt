package com.duda.adidaschallenge.infrastructure.database.mongo

import com.duda.adidaschallenge.infrastructure.model.StockMongo
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface StockMongoDBRepository: ReactiveCrudRepository<StockMongo, String>{
    @Query("{ 'productId' : ?0 }")
    fun findByProductId(productId: String): Mono<StockMongo>
}