package com.duda.adidaschallenge.application.rest

import com.duda.adidaschallenge.application.projection.request.ReservationRequest
import com.duda.adidaschallenge.application.projection.request.StockRequest
import com.duda.adidaschallenge.application.projection.response.ProductResponse
import com.duda.adidaschallenge.application.projection.response.ReservationResponse
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
    fun registerStock(@PathVariable id: Int, @RequestBody request: StockRequest) =
        stockService.register(id, request.toModel())

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = ["/{id}"])
    fun findOne(@PathVariable id: Int): Mono<ProductResponse> =
        productService.findOne(id)
            .map { product ->
                ProductResponse(product.stock.total, product.stock.reserved, product.stock.sold)
            }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/reserve"])
    fun reserve(@PathVariable id: Int): Mono<ReservationResponse> =
        stockService.reserve(id)
            .map { reservation ->
                ReservationResponse(reservation)
            }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/unreserve"])
    fun unReserve(@PathVariable id: Int, request: ReservationRequest) =
        stockService.unReserve(id, request)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = ["/{id}/sold"])
    fun sell(@PathVariable id: Int, request: ReservationRequest) =
        stockService.sell(id, request)
}