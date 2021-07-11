package com.duda.adidaschallenge.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ReserveQuantityExceededException:
    ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserve quantity already reach stock limit")
