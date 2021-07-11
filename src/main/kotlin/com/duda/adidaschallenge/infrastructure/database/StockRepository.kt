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
            .map { Stock(it.id, it.total, it.productId) }
            .switchIfEmpty(Mono.error(StockNotFoundForProductException(productId)))

    fun save(stock: Stock) {
        stockMongoDBRepository.save(StockMongo(stock.id, stock.productId, stock.total))
    }
}
