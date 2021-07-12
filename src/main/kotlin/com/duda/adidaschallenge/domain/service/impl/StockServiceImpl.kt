package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.StockService
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class StockServiceImpl(private val productRepository: ProductRepository,
                       private val stockRepository: StockRepository) : StockService {

    override fun findByProductId(productId: String): Mono<Stock> =
        stockRepository.findByProductId(productId)

    @Transactional
    override fun register(newStock: Stock): Mono<Void> =
        productRepository.findById(newStock.productId)
            .map { deleteStockFromProduct(newStock.productId) }
            .flatMap { stockRepository.save(newStock) }
            .then()

    private fun deleteStockFromProduct(productId: String) {
        stockRepository.findByProductId(productId)
            .map { productRepository.delete(it.id) }
    }

}