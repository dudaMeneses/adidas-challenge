package com.duda.adidaschallenge.application.rest.handler

import com.duda.adidaschallenge.application.projection.request.ReservationRequest
import com.duda.adidaschallenge.application.projection.response.ReservationResponse
import com.duda.adidaschallenge.domain.service.ReserveService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ReserveHandler(private val reserveService: ReserveService) {
    fun reserve(request: ServerRequest): Mono<ServerResponse> {
        val response = reserveService.reserve(request.pathVariable("id"))
            .map { reservation ->
                ReservationResponse(reservation)
            }

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromPublisher(response, ReservationResponse::class.java))
    }

    fun unreserve(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(ReservationRequest::class.java)
            .doOnNext { reserve ->
                val productId = request.pathVariable("id")
                reserveService.unreserve(productId, reserve.reservationToken)
            }
            .then(ServerResponse.ok().build())
}
