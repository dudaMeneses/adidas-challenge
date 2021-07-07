package com.duda.adidaschallenge.domain.service

import com.duda.adidaschallenge.domain.model.Product
import reactor.core.publisher.Mono

interface ProductService {
    fun findOne(id: String): Mono<Product>
}