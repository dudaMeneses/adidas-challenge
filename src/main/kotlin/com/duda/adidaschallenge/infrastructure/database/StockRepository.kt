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
            .map { Stock(it.id, it.total, it.productId) }

    fun save(stock: Stock): Mono<Stock> =
        stockMongoDBRepository.save(StockMongo(stock.id, stock.productId, stock.total))
            .map { Stock(it.id, it.total, it.productId) }

    fun adjustStockAfterSell(stockId: String?): Mono<Stock>? =
        stockId?.let { it ->
            stockMongoDBRepository.findById(it)
                .flatMap { stockMongoDBRepository.save(it.copy(total = it.total - 1)) }
                .map { Stock(it.id, it.total, it.productId) }
        }

    fun delete(stock: Stock) =
        stock.id?.let { stockMongoDBRepository.deleteById(it) }

}
