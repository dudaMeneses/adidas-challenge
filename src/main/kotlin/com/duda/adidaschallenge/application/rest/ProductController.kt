package com.duda.adidaschallenge.application.rest

import com.duda.adidaschallenge.application.projection.ProductResponse
import com.duda.adidaschallenge.application.projection.request.ProductRequest
import com.duda.adidaschallenge.application.projection.request.ReservationRequest
import com.duda.adidaschallenge.application.projection.request.StockRequest
import com.duda.adidaschallenge.application.projection.response.ReservationResponse
import com.duda.adidaschallenge.application.projection.response.StockResponse
import com.duda.adidaschallenge.application.rest.handler.ProductHandler
import com.duda.adidaschallenge.application.rest.handler.ReserveHandler
import com.duda.adidaschallenge.application.rest.handler.StockHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@Configuration
@RestController
@RequestMapping(path = ["/product"],
                produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val stockHandler: StockHandler,
                        private val productHandler: ProductHandler,
                        private val reserveHandler: ReserveHandler) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(path = ["/{id}/stock"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerStock(@PathVariable id: String, @RequestBody request: StockRequest) =
        stockHandler.register(id, request.stock)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = ["/{id}"])
    fun findOne(@PathVariable id: String): Mono<StockResponse> =
        stockHandler.findByProductId(id)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/reserve"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun reserve(@PathVariable id: String): Mono<ReservationResponse> =
        reserveHandler.reserve(id)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/unreserve"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun unReserve(@PathVariable id: String, @RequestBody request: ReservationRequest) =
        reserveHandler.unreserve(id, request.reservationToken)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/sold"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun sell(@PathVariable id: String, @RequestBody request: ReservationRequest) =
        productHandler.sell(id, request.reservationToken)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createProduct(@RequestBody request: ProductRequest): Mono<ProductResponse> =
        productHandler.create(request)
            .map { product -> ProductResponse(product.id, product.name)  }
}