package com.duda.adidaschallenge.domain.service

import com.duda.adidaschallenge.application.projection.request.ReservationRequest
import com.duda.adidaschallenge.domain.model.Stock
import reactor.core.publisher.Mono

interface StockService {
    fun register(productId: Int, stock: Stock)
    fun reserve(productId: Int): Mono<String>
    fun unReserve(productId: Int, request: ReservationRequest)
    fun sell(id: Int, request: ReservationRequest)
}
