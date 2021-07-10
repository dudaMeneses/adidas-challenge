package com.duda.adidaschallenge.infrastructure.database.mongo

import com.duda.adidaschallenge.infrastructure.model.ReserveMongo
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ReserveMongoDBRepository: ReactiveCrudRepository<ReserveMongo, String> {
    fun findByStockId(stockId: String?): Flux<ReserveMongo>
}