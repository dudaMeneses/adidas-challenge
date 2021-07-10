package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ReserveService
import com.duda.adidaschallenge.domain.service.exception.ReserveQuantityExceededException
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReserveServiceImpl(private val reserveRepository: ReserveRepository,
                         private val stockRepository: StockRepository) : ReserveService {
    override fun reserve(productId: String): Mono<String> =
        stockRepository.findByProductId(productId)
            .flatMap { stock -> validateReserveQuantity(stock) }
            .map { stock -> reserve(stock) }

    override fun unreserve(productId: String, token: String) {
        stockRepository.findByProductId(productId)
            .map { reserveRepository.unreserve(token) }
    }

    override fun findByProductId(productId: String): List<Reserve> {
        TODO("Not yet implemented")
    }

    private fun validateReserveQuantity(stock: Stock): Mono<Stock> =
        Mono.just(stock)
            .map { s ->
                reserveRepository.findByStockId(s.id)
                    .collectList()
                    .block()?.let { s.copy(reserves = it) }
            }
            .filter { s -> s.total > s.getReserved() }
            .switchIfEmpty(Mono.error(ReserveQuantityExceededException()))

    private fun reserve(stock: Stock): String {
        TODO("Not yet implemented")
    }

}