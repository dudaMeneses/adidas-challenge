package com.duda.adidaschallenge.application.rest.handler

import com.duda.adidaschallenge.application.projection.response.ReservationResponse
import com.duda.adidaschallenge.domain.service.ReserveService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ReserveHandler(private val reserveService: ReserveService) {
    fun reserve(productId: String): Mono<ReservationResponse> =
        reserveService.reserve(productId)
            .map { ReservationResponse(it) }

    fun unreserve(productId: String, token: String) {
        reserveService.unreserve(productId, token)
    }
}
