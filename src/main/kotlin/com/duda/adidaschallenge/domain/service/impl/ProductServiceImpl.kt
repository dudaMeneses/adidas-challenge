package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ProductService
import com.duda.adidaschallenge.domain.service.exception.ProductNotFoundException
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun findOne(id: String): Mono<Product> =
        productRepository.findById(id).map {
            product -> Product(product.id, product.stock)
        }.switchIfEmpty(Mono.error { ProductNotFoundException(id) })
}