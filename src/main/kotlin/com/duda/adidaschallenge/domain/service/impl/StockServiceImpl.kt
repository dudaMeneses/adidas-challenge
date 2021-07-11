package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ReserveService
import com.duda.adidaschallenge.domain.service.StockService
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StockServiceImpl(private val stockRepository: StockRepository,
                       private val reserveService: ReserveService) : StockService {

    override fun findByProductId(productId: String): Mono<Stock> {
        return stockRepository.findByProductId(productId);
    }

    override fun register(newStock: Stock): Mono<Void> =
        stockRepository.findByProductId(newStock.productId)
            .defaultIfEmpty(Stock(productId = newStock.productId))
            .flatMap { reserveService.validateReserveQuantity(it) }
            .map { it.copy(total = newStock.total) }
            .doOnNext { stockRepository.save(it) }
            .then()

}