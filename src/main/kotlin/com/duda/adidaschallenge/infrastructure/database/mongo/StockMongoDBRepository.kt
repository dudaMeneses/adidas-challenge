package com.duda.adidaschallenge.infrastructure.database.mongo

import com.duda.adidaschallenge.infrastructure.model.StockMongo
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface StockMongoDBRepository: ReactiveCrudRepository<StockMongo, String>{
    fun findByProductId(productId: String): Mono<StockMongo>
}