package com.duda.adidaschallenge.infrastructure.database

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.exception.ReserveNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.mongo.ReserveMongoDBRepository
import com.duda.adidaschallenge.infrastructure.model.ReserveMongo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ReserveRepository(private val reserveMongoDBRepository: ReserveMongoDBRepository) {

    fun findByStockId(stockId: String?): Flux<Reserve> =
        reserveMongoDBRepository.findByStockId(stockId)
            .map { Reserve(it.id, it.stockId, it.sold) }

    fun unreserve(token: String) {
        TODO("Not yet implemented")
    }

    fun findByIdAndStockId(token: String, stock: Stock): Mono<Reserve> =
        reserveMongoDBRepository.findByIdAndStockId(token, stock.id)
            .switchIfEmpty(Mono.error(ReserveNotFoundForProductException(token, stock.productId)))
            .map { Reserve(it.id, it.stockId, it.sold) }

    fun reserve(stockId: String?): Mono<Reserve> =
        reserveMongoDBRepository.save(ReserveMongo(stockId = stockId))
            .map { Reserve(it.id, it.stockId, it.sold) }

    fun sell(reserve: Reserve) {
        reserveMongoDBRepository.save(ReserveMongo(reserve.id, reserve.stockId, reserve.sold))
    }

}
