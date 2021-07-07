package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.application.projection.request.ReservationRequest
import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.StockService
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StockServiceImpl(private val productRepository: ProductRepository): StockService {
    override fun register(productId: String, stock: Stock) {
        productRepository.findById(productId)
            .map { product -> this.validateReserveQuantity(product) }
            .map { product -> product.copy(stock = stock)}
            .doOnNext { product -> productRepository.register(product) }
    }

    override fun reserve(productId: String): Mono<String> =
        productRepository.findById(productId)
            .map { product -> this.validateReserveQuantity(product) }
            .map { product -> this.reserve(product)}

    override fun unReserve(productId: String, token: String) {
        Mono.zip(productRepository.findReservation(productId, token), productRepository.findById(productId))
            .map { tuple -> this.unreserve(tuple.t1, tuple.t2) }
            .doOnNext { product -> productRepository.save(product) }
    }

    override fun sell(id: String, request: ReservationRequest) {
        TODO("Not yet implemented")
    }

    private fun validateReserveQuantity(product: Product): Product {
        TODO("Not yet implemented")
    }

    private fun reserve(product: Product): String {
        TODO("Not yet implemented")
    }

    private fun unreserve(reserve: Reserve, product: Product): Product {
        TODO("Not yet implemented")
    }
}