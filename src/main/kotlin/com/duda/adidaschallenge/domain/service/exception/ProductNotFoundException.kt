package com.duda.adidaschallenge.domain.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

class ProductNotFoundException(id: String): HttpStatusCodeException(HttpStatus.NOT_FOUND, "Product with id $id not found")
