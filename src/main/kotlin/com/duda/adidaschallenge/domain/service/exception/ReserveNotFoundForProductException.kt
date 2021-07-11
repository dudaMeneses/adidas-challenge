package com.duda.adidaschallenge.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ReserveNotFoundForProductException(token: String, productId: String):
    ResponseStatusException(HttpStatus.NOT_FOUND, "Reserve $token not found for product $productId")
