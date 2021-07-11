package com.duda.adidaschallenge.infrastructure.database.mongo

import com.duda.adidaschallenge.infrastructure.model.ReserveMongo
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReserveMongoDBRepository: ReactiveCrudRepository<ReserveMongo, String> {
    @Query("{ 'stockId' : ?0 }")
    fun findByStockId(stockId: String?): Flux<ReserveMongo>

    @Query("{ 'id' : ?0, 'stockId': ?1 }")
    fun findByIdAndStockId(token: String, id: String?): Mono<ReserveMongo>
}