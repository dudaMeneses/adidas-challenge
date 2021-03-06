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
        productMongoDBRepository.save(product.toRequest())
            .map { it.toModel() }

    fun findById(id: String): Mono<Product> =
        productMongoDBRepository.findById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException(id)))
            .map { it.toModel() }

    fun ProductMongo.toModel(): Product = Product(this.id, this.name)
    fun Product.toRequest(): ProductMongo = ProductMongo(name = this.name)
}