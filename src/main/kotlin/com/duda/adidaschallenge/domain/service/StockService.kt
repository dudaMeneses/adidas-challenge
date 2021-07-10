package com.duda.adidaschallenge.domain.service

import com.duda.adidaschallenge.domain.model.Stock
import reactor.core.publisher.Mono

interface StockService {
    fun findByProductId(productId: String): Mono<Stock>
    fun register(newStock: Stock)
}
