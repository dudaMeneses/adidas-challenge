package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.exception.ReserveQuantityExceededException
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ReserveServiceReserveTest {

    private lateinit var reserveService: ReserveServiceImpl
    private val reserveRepository: ReserveRepository = mock()
    private val stockRepository: StockRepository = mock()

    @BeforeEach
    fun init() {
        reserveService = ReserveServiceImpl(reserveRepository, stockRepository)
    }

    @Test
    fun whenStockNotFoundForProduct_thenStockNotFoundForProductException(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenAnswer { Mono.error<Throwable>(StockNotFoundForProductException("123")) }

        StepVerifier.create(reserveService.reserve("132"))
            .verifyError(StockNotFoundForProductException::class.java)
    }

    @Test
    fun whenReserveAlreadyOnLimit_thenReserveQuantityExceededException(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(Stock(id = "456", productId = "123", total = 1)))

        whenever(reserveRepository.findByStockId("456"))
            .thenReturn(Flux.just(Reserve(id = "999", stockId = "456", sold = false)))

        StepVerifier.create(reserveService.reserve("132"))
            .verifyError(ReserveQuantityExceededException::class.java)
    }

    @Test
    fun whenHappyPath_thenReturnReserveToken(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(Stock(id = "456", productId = "123", total = 2)))

        whenever(reserveRepository.findByStockId("456"))
            .thenReturn(Flux.just(Reserve(id = "999", stockId = "456", sold = false)))

        whenever(reserveRepository.reserve("456"))
            .thenReturn(Mono.just(Reserve(id = "888", sold = false, stockId = "456")))

        StepVerifier.create(reserveService.reserve("132"))
            .expectNext("888")
    }
}