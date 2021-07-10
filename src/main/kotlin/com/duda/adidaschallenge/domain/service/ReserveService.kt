package com.duda.adidaschallenge.domain.service

import com.duda.adidaschallenge.domain.model.Reserve
import reactor.core.publisher.Mono

interface ReserveService {
    fun reserve(productId: String): Mono<String>
    fun unreserve(productId: String, token: String)
    fun findByProductId(productId: String): List<Reserve>
}
