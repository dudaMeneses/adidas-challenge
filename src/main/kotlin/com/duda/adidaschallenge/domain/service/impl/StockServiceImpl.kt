package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ReserveService
import com.duda.adidaschallenge.domain.service.StockService
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StockServiceImpl(private val stockRepository: StockRepository,
                       private val reserveService: ReserveService) : StockService {

    override fun findByProductId(productId: String): Mono<Stock> {
        return stockRepository.findByProductId(productId);
    }

    override fun register(newStock: Stock) {
        stockRepository.findByProductId(newStock.productId)
            .map { s -> s.copy(reserves = reserveService.findByProductId(s.productId)) }
            .map { s -> this.validateReserveQuantity(s) }
            .map { s -> s.copy(total = newStock.total) }
            .doOnNext { s -> stockRepository.save(s) }
    }

    private fun validateReserveQuantity(stock: Stock): Stock {
        TODO("Not yet implemented")
    }
}