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
            .map { it.toModel() }

    fun unreserve(token: String): Mono<Void> =
        reserveMongoDBRepository.deleteById(token)

    fun findByIdAndStockId(token: String, stock: Stock): Mono<Reserve> =
        reserveMongoDBRepository.findByIdAndStockId(token, stock.id)
            .switchIfEmpty(Mono.error(ReserveNotFoundForProductException(token, stock.productId)))
            .map { it.toModel() }

    fun reserve(stockId: String): Mono<Reserve> =
        reserveMongoDBRepository.save(ReserveMongo(stockId = stockId))
            .map {it.toModel() }

    fun sell(reserve: Reserve): Mono<Reserve> =
        reserveMongoDBRepository.save(ReserveMongo(reserve.id, reserve.stockId, true))
            .map { it.toModel() }

    fun save(reserve: Reserve): Mono<Reserve> =
        reserveMongoDBRepository.save(ReserveMongo(reserve.id, reserve.stockId, reserve.sold))
            .map { it.toModel() }

    fun ReserveMongo.toModel(): Reserve = Reserve(this.id, this.stockId, this.sold)

}
