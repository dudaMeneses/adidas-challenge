package com.duda.adidaschallenge.domain.service

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReserveService {
    fun reserve(productId: String): Mono<String>
    fun unreserve(productId: String, token: String): Mono<Void>
    fun findByStockId(stockId: String?): Flux<Reserve>
    fun validateReserveQuantity(stock: Stock): Mono<Stock>
}
