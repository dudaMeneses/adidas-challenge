package com.duda.adidaschallenge.application.projection.response

import com.duda.adidaschallenge.domain.model.Stock

data class ReservationResponse(val reservationToken: String) {
    fun of(reservationToken: String): ReservationResponse = ReservationResponse(reservationToken)
}