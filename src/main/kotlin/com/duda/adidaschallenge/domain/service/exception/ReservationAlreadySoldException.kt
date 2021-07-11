package com.duda.adidaschallenge.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ReservationAlreadySoldException(token: String):
    ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation $token already sold")
