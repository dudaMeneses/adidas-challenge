package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.exception.ReserveNotFoundForProductException
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ReserveServiceUnreserveTest {
    private lateinit var reserveService: ReserveServiceImpl
    private val stockRepository: StockRepository = mock()
    private val reserveRepository: ReserveRepository = mock()

    @BeforeEach
    fun init(){
        reserveService = ReserveServiceImpl(reserveRepository, stockRepository)
    }

    @Test
    fun whenStockNotFoundForProduct_returnStockNotFoundForProductException(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenAnswer { Mono.error<Throwable>(StockNotFoundForProductException("123")) }

        StepVerifier.create(reserveService.unreserve("123", "999"))
            .verifyError(StockNotFoundForProductException::class.java)
    }

    @Test
    fun whenReserveNotFound_returnReserveNotFoundForProductException(){
        val stock = Stock(id = "456", total = 2, productId = "123")

        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(stock))

        whenever(reserveRepository.findByIdAndStockId(anyString(), any()))
            .thenAnswer { Mono.error<Throwable>(ReserveNotFoundForProductException("999", "123")) }

        StepVerifier.create(reserveService.unreserve("123", "999"))
            .verifyError(ReserveNotFoundForProductException::class.java)
    }

    @Test
    fun whenHappyPath_thenCallUnreserve(){
        val stock = Stock(id = "456", total = 2, productId = "123")

        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(stock))

        whenever(reserveRepository.findByIdAndStockId("999", stock))
            .thenReturn(Mono.just(Reserve(id = "999", sold = false, stockId = "456")))

        reserveService.unreserve("123", "999").block()

        verify(reserveRepository).unreserve("999")
    }
}