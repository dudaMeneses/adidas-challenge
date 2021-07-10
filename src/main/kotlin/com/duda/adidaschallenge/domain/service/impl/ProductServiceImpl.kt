package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.service.ProductService
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun sell(productId: String, reservationToken: String) {
        TODO("Not yet implemented")
    }

    override fun save(product: Product): Mono<Product> =
        productRepository.save(product)
}