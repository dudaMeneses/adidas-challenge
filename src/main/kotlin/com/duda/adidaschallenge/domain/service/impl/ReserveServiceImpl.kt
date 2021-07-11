package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ReserveService
import com.duda.adidaschallenge.domain.service.exception.ReserveQuantityExceededException
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReserveServiceImpl(private val reserveRepository: ReserveRepository,
                         private val stockRepository: StockRepository) : ReserveService {
    override fun reserve(productId: String): Mono<String> =
        stockRepository.findByProductId(productId)
            .flatMap { validateReserveQuantity(it) }
            .flatMap { reserveRepository.reserve(it.id) }
            .map { it.id }

    override fun unreserve(productId: String, token: String): Mono<Void> =
        stockRepository.findByProductId(productId)
            .map { reserveRepository.findByIdAndStockId(token, it) }
            .map { reserveRepository.unreserve(token) }
            .then()

    override fun findByStockId(stockId: String?): Flux<Reserve> =
        reserveRepository.findByStockId(stockId)

    override fun validateReserveQuantity(stock: Stock): Mono<Stock> =
        Mono.just(stock)
            .map { s ->
                reserveRepository.findByStockId(s.id)
                    .collectList()
                    .block()?.let { s.copy(reserves = it) }
            }
            .filter { it.total > it.reserves.size }
            .switchIfEmpty(Mono.error(ReserveQuantityExceededException()))

}