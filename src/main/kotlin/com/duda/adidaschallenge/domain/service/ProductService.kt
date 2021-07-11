package com.duda.adidaschallenge.domain.service

import com.duda.adidaschallenge.domain.model.Product
import reactor.core.publisher.Mono

interface ProductService {
    fun sell(productId: String, token: String): Mono<Void>
    fun save(product: Product): Mono<Product>
}