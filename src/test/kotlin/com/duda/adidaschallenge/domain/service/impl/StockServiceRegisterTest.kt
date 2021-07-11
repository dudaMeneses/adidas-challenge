package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.ReserveService
import com.duda.adidaschallenge.domain.service.exception.ReserveQuantityExceededException
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import net.bytebuddy.implementation.bytecode.Throw
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class StockServiceRegisterTest {

    private lateinit var stockService: StockServiceImpl
    private val stockRepository: StockRepository = mock()
    private val reserveService: ReserveService = mock()

    @BeforeEach
    fun init(){
        stockService = StockServiceImpl(stockRepository, reserveService)
    }

    @Test
    fun whenNoStockFound_thenRegisterNewStock(){
        val stock = Stock(total = 10, productId = "123", reserves = listOf())

        whenever(stockRepository.findByProductId(anyString()))
            .thenAnswer { Mono.error<Throwable>(StockNotFoundForProductException("123")) }

        whenever(reserveService.validateReserveQuantity(any()))
            .thenReturn(Mono.just(stock))

        stockService.register(stock).block()

        verify(stockRepository).save(stock)
    }

    @Test
    fun whenStockFoundAndReservesMoreThanNewStock_thenReserveQuantityExceededException(){
        val stock = Stock(total = 10, productId = "123", reserves = listOf())

        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(stock))

        whenever(reserveService.validateReserveQuantity(any()))
            .thenAnswer { Mono.error<Throwable>(ReserveQuantityExceededException()) }

        StepVerifier.create(stockService.register(stock.copy(total = 5)))
            .expectError(ReserveQuantityExceededException::class.java)
    }

    @Test
    fun whenHappyPath_thenSaveNewStock(){
        val stock = Stock(total = 10, productId = "123", reserves = listOf())

        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(stock))

        whenever(reserveService.validateReserveQuantity(any()))
            .thenReturn(Mono.just(stock))

        stockService.register(stock).block()

        verify(stockRepository).save(stock)
    }

}