package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ReserveService
import com.duda.adidaschallenge.domain.service.StockService
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class StockServiceImpl(private val productRepository: ProductRepository,
                       private val stockRepository: StockRepository,
                       private val reserveService: ReserveService) : StockService {

    override fun findByProductId(productId: String): Mono<Stock> =
        stockRepository.findByProductId(productId)

    @Transactional
    override fun register(newStock: Stock): Mono<Void> =
        productRepository.findById(newStock.productId)
            .flatMap { stockRepository.findByProductId(newStock.productId) }
            .onErrorResume(StockNotFoundForProductException::class.java) { Mono.just(newStock) }
            .flatMap { reserveService.validateReserveQuantity(it) }
            .map { it.copy(total = newStock.total) }
            .doOnNext { stockRepository.save(it) }
            .then()

}