package com.duda.adidaschallenge.infrastructure.database.mongo

import com.duda.adidaschallenge.infrastructure.model.ReserveMongo
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface ReserveMongoDBRepository: ReactiveCrudRepository<ReserveMongo, String> {
    fun findByStockId(stockId: String?): Flux<ReserveMongo>
    fun findByIdAndStockId(token: String, id: String?): Mono<ReserveMongo>
}