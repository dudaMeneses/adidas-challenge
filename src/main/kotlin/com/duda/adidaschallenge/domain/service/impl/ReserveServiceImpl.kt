package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ReserveService
import com.duda.adidaschallenge.domain.service.exception.ReserveQuantityExceededException
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReserveServiceImpl(private val reserveRepository: ReserveRepository,
                         private val stockRepository: StockRepository) : ReserveService {
    @Transactional
    override fun reserve(productId: String): Mono<String> =
        stockRepository.findByProductId(productId)
            .flatMap { validateReserveQuantity(it) }
            .flatMap { reserveRepository.reserve(it.id!!) }
            .map { it.id }

    @Transactional
    override fun unreserve(productId: String, token: String): Mono<Void> =
        stockRepository.findByProductId(productId)
            .flatMap { reserveRepository.findByIdAndStockId(token, it) }
            .flatMap { reserveRepository.unreserve(token) }

    override fun findByStockId(stockId: String?): Flux<Reserve> =
        reserveRepository.findByStockId(stockId)

    private fun validateReserveQuantity(stock: Stock): Mono<Stock> =
        Mono.just(stock)
            .map { reserveRepository.findByStockId(it.id) }
            .flatMap { it.collectList() }
            .map { stock.copy(reserves = it) }
            .filter { it.total > it.reserves.size }
            .switchIfEmpty(Mono.error(ReserveQuantityExceededException()))

}