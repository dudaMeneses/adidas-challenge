package com.duda.adidaschallenge.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ProductNotFoundException(id: String): ResponseStatusException(HttpStatus.NOT_FOUND, "Product $id not found")
