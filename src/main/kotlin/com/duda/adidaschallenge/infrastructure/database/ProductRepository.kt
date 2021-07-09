package com.duda.adidaschallenge.infrastructure.database

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.exception.ProductNotFoundException
import com.duda.adidaschallenge.infrastructure.database.mongo.ProductMongoDBRepository
import com.duda.adidaschallenge.infrastructure.model.ProductMongo
import com.duda.adidaschallenge.infrastructure.model.StockMongo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ProductRepository(private val productMongoDBRepository: ProductMongoDBRepository) {
    fun findById(id: String): Mono<Product> =
        productMongoDBRepository.findById(id)
            .map { product ->
                Product(product.id,
                        Stock(product.stock.total, product.stock.reserves.map { reserve ->
                            Reserve(reserve.id, reserve.sold)
                        })
                )
            }

    fun register(product: Product) {
        productMongoDBRepository.save(ProductMongo(product.id, StockMongo(product.stock.total)))
    }

    fun unreserve(productId: String, token: String) {
        productMongoDBRepository.findById(productId)
            .switchIfEmpty(Mono.error { ProductNotFoundException(productId) })
            .map { product -> this.removeReserve(product, token) }
            .doOnNext { product -> productMongoDBRepository.save(product) }
    }

    fun sell(productId: String, token: String) {
        productMongoDBRepository.findById(productId)
            .switchIfEmpty(Mono.error { ProductNotFoundException(productId) })
            .map { product -> this.sellReserve(product, token) }
            .doOnNext { product -> productMongoDBRepository.save(product) }
    }

    private fun sellReserve(product: ProductMongo, token: String) : ProductMongo =
        product.copy(stock = product.stock.copy(reserves =
            product.stock.reserves
                .map { reserve -> if (!reserve.sold && reserve.id.equals(token)) reserve.copy(sold = true) else reserve }
        ))

    private fun removeReserve(product: ProductMongo, token: String): ProductMongo =
        product.copy(stock = product.stock.copy(reserves = product.stock.reserves.filter { reserve -> !reserve.sold && !reserve.id.equals(token)}))

}