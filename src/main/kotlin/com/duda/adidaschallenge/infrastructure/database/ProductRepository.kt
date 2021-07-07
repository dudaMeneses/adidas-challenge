package com.duda.adidaschallenge.infrastructure.database

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.infrastructure.database.mongo.ProductMongoDBRepository
import com.duda.adidaschallenge.infrastructure.model.ProductMongo
import com.duda.adidaschallenge.infrastructure.model.ReserveMongo
import com.duda.adidaschallenge.infrastructure.model.StockMongo
import reactor.core.publisher.Mono

class ProductRepository(private val productMongoDBRepository: ProductMongoDBRepository) {
    fun findById(id: String): Mono<Product> =
        productMongoDBRepository.findById(id)
            .map { product ->
                Product(product.id,
                        Stock(product.stock.total, product.stock.reserves.map { reserve ->
                            Reserve(reserve.id, reserve.token, reserve.sold)
                        })
                )
            }

    fun register(product: Product) {
        productMongoDBRepository.save(ProductMongo(product.id, StockMongo(product.stock.total)))
    }

    fun findReservation(productId: String, token: String): Mono<Reserve> {
        TODO("Not yet implemented")
    }

    fun save(product: Product) {
        productMongoDBRepository.save(ProductMongo(product.id,
            StockMongo(product.stock.id,
                       product.stock.total,
                       product.stock.reserves.map { reserve ->
                           ReserveMongo(reserve.id, reserve.token, reserve.sold)
                       })
        ))
    }
}