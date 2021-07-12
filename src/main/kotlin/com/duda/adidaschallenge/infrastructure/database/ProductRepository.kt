package com.duda.adidaschallenge.infrastructure.database

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.service.exception.ProductNotFoundException
import com.duda.adidaschallenge.infrastructure.database.mongo.ProductMongoDBRepository
import com.duda.adidaschallenge.infrastructure.model.ProductMongo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ProductRepository(private val productMongoDBRepository: ProductMongoDBRepository) {
    fun save(product: Product): Mono<Product> =
        productMongoDBRepository.save(ProductMongo(name = product.name))
            .map { Product(id = it.id, name = it.name) }

    fun findById(id: String): Mono<Product> =
        productMongoDBRepository.findById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException(id)))
            .map { Product(id = it.id, name = it.name) }

    fun delete(id: String?) {
        id?.let { productMongoDBRepository.deleteById(it) }
    }
}