package com.duda.adidaschallenge.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class StockNotFoundForProductException(productId: String):
    ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found for product $productId")
