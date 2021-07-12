package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.exception.ProductNotFoundException
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
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

class StockServiceRegisterTest {

    private lateinit var stockService: StockServiceImpl
    private val stockRepository: StockRepository = mock()
    private val productRepository: ProductRepository = mock()

    @BeforeEach
    fun init(){
        stockService = StockServiceImpl(productRepository, stockRepository)
    }

    @Test
    fun whenProductNotFound_thenReturnProductNotFoundException(){
        whenever(productRepository.findById(anyString()))
            .thenAnswer { Mono.error<Throwable>(ProductNotFoundException("123")) }

        StepVerifier.create(stockService.register(Stock(productId = "123", total = 10)))
            .verifyError(ProductNotFoundException::class.java)
    }

    @Test
    fun whenNoStockFound_thenRegisterNewStock(){
        val stock = Stock(total = 10, productId = "123", reserves = listOf())

        whenever(productRepository.findById(anyString()))
            .thenReturn(Mono.just(Product("123", "test")))

        whenever(stockRepository.findByProductId(anyString()))
            .thenAnswer { Mono.error<Throwable>(StockNotFoundForProductException("123")) }

        whenever(stockRepository.save(any()))
            .thenReturn(Mono.just(stock))

        stockService.register(stock).block()

        verify(stockRepository).save(stock)
    }

    @Test
    fun whenHappyPath_thenSaveNewStock(){
        val stock = Stock(total = 10, productId = "123", reserves = listOf())

        whenever(productRepository.findById(anyString()))
            .thenReturn(Mono.just(Product("123", "test")))

        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(stock))

        whenever(stockRepository.save(any()))
            .thenReturn(Mono.just(stock))

        stockService.register(stock).block()

        verify(stockRepository).save(stock)
    }

}