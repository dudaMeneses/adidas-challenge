package com.duda.adidaschallenge.application.rest.handler

import com.duda.adidaschallenge.application.projection.request.StockRequest
import com.duda.adidaschallenge.application.projection.response.StockResponse
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.StockService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class StockHandler(private val stockService: StockService) {
    fun findByProductId(request: ServerRequest): Mono<ServerResponse> {
        val response = stockService.findByProductId(request.pathVariable("id"))
            .map { stock ->
                StockResponse(stock.total, stock.getReserved(), stock.getSold())
            }

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromPublisher(response, StockResponse::class.java))
    }

    fun register(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(StockRequest::class.java)
            .doOnNext { stockRequest ->
                val productId = request.pathVariable("id")
                stockService.register(Stock(total = stockRequest.stock, productId = productId))
            }
            .then(ServerResponse.noContent().build())
}
