package com.duda.adidaschallenge.application.rest.handler

import com.duda.adidaschallenge.application.projection.response.StockResponse
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.StockService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class StockHandler(private val stockService: StockService) {
    fun findByProductId(productId: String): Mono<StockResponse> =
        stockService.findByProductId(productId)
            .map { it.toResponse() }

    fun register(productId: String, stock: Int) =
        stockService.register(Stock(total = stock, productId = productId))

    fun Stock.toResponse(): StockResponse = StockResponse(this.total, this.getReserve(), this.getSold())
}
