package com.duda.adidaschallenge.domain.service

interface ProductService {
    fun sell(productId: String, reservationToken: String)
}