package com.duda.adidaschallenge.domain.service

import com.duda.adidaschallenge.domain.model.Stock
import reactor.core.publisher.Mono

interface StockService {
    fun register(productId: String, stock: Stock)
    fun reserve(productId: String): Mono<String>
    fun unReserve(productId: String, token: String)
    fun sell(productId: String, token: String)
}
