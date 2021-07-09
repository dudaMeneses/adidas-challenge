package com.duda.adidaschallenge.application.rest

import com.duda.adidaschallenge.application.projection.request.ReservationRequest
import com.duda.adidaschallenge.application.projection.request.StockRequest
import com.duda.adidaschallenge.application.projection.response.ProductResponse
import com.duda.adidaschallenge.application.projection.response.ReservationResponse
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ProductService
import com.duda.adidaschallenge.domain.service.StockService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/product"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val stockService: StockService, private val productService: ProductService) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(path = ["/{id}/stock"])
    fun registerStock(@PathVariable id: String, @RequestBody request: StockRequest) =
        stockService.register(id, Stock(null, request.stock, listOf()))

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = ["/{id}"])
    fun findOne(@PathVariable id: String): Mono<ProductResponse> =
        productService.findOne(id)
            .map { product ->
                ProductResponse(product.stock.total, product.stock.getReserved(), product.stock.getSold())
            }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/reserve"])
    fun reserve(@PathVariable id: String): Mono<ReservationResponse> =
        stockService.reserve(id)
            .map { reservation ->
                ReservationResponse(reservation)
            }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/unreserve"])
    fun unReserve(@PathVariable id: String, request: ReservationRequest) =
        stockService.unReserve(id, request.reservationToken)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/sold"])
    fun sell(@PathVariable id: String, request: ReservationRequest) =
        stockService.sell(id, request.reservationToken)
}