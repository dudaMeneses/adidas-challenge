package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.service.ProductService
import com.duda.adidaschallenge.domain.service.exception.ReservationAlreadySoldException
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class ProductServiceImpl(private val productRepository: ProductRepository,
                         private val reserveRepository: ReserveRepository,
                         private val stockRepository: StockRepository) : ProductService {
    @Transactional
    override fun sell(productId: String, token: String): Mono<Void> =
        stockRepository.findByProductId(productId)
            .flatMap { reserveRepository.findByIdAndStockId(token, it) }
            .filter { !it.sold }
            .switchIfEmpty(Mono.error(ReservationAlreadySoldException(token)))
            .flatMap { reserveRepository.sell(Reserve(it.id, it.stockId)) }
            .doOnNext { stockRepository.adjustStockAfterSell(it.stockId) }
            .then()

    @Transactional
    override fun save(product: Product): Mono<Product> =
        productRepository.save(product)
}