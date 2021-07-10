package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.service.ProductService
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun sell(productId: String, reservationToken: String) {
        TODO("Not yet implemented")
    }
}