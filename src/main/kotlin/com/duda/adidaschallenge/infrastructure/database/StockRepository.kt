package com.duda.adidaschallenge.infrastructure.database

import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.mongo.StockMongoDBRepository
import com.duda.adidaschallenge.infrastructure.model.StockMongo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class StockRepository(private val stockMongoDBRepository: StockMongoDBRepository) {

    fun findByProductId(productId: String): Mono<Stock> =
        stockMongoDBRepository.findByProductId(productId)
            .switchIfEmpty(Mono.error(StockNotFoundForProductException(productId)))
            .map { it.toModel() }

    fun save(stock: Stock): Mono<Stock> =
        stockMongoDBRepository.save(stock.toRequest())
            .map { it.toModel() }

    fun adjustStockAfterSell(stockId: String): Mono<Stock> =
        stockMongoDBRepository.findById(stockId)
            .flatMap { stockMongoDBRepository.save(it.apply { total -= 1 }) }
            .map { it.toModel() }

    fun StockMongo.toModel(): Stock = Stock(this.id, this.total, this.productId)
    fun Stock.toRequest(): StockMongo = StockMongo(this.id, this.productId, this.total)
}
