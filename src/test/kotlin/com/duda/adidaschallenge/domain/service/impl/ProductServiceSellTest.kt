package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.domain.service.exception.ReservationAlreadySoldException
import com.duda.adidaschallenge.domain.service.exception.ReserveNotFoundForProductException
import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
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

class ProductServiceSellTest {
    private lateinit var productService: ProductServiceImpl
    private val productRepository: ProductRepository = mock()
    private val stockRepository: StockRepository = mock()
    private val reserveRepository: ReserveRepository = mock()

    @BeforeEach
    fun init(){
        productService = ProductServiceImpl(productRepository, reserveRepository, stockRepository)
    }

    @Test
    fun whenStockNotFound_thenStockNotFoundForProductException(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenAnswer { Mono.error<Throwable>(StockNotFoundForProductException("123")) }

        StepVerifier.create(productService.sell("123", "321"))
            .verifyError(StockNotFoundForProductException::class.java)
    }

    @Test
    fun whenNoReserveInStock_thenReserveNotFoundException(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(Stock(id = "456", total = 10, productId = "123")))

        whenever(reserveRepository.findByIdAndStockId(anyString(), any()))
            .thenAnswer { Mono.error<Throwable>(ReserveNotFoundForProductException("321", "123")) }

        StepVerifier.create(productService.sell("123", "321"))
            .verifyError(ReserveNotFoundForProductException::class.java)
    }

    @Test
    fun whenReserveIsSold_thenReservationAlreadySoldException(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(Stock(id = "456", total = 10, productId = "123")))

        whenever(reserveRepository.findByIdAndStockId(anyString(), any()))
            .thenReturn(Mono.just(Reserve(id = "321",stockId = "456", sold = true)))

        StepVerifier.create(productService.sell("123", "321"))
            .verifyError(ReservationAlreadySoldException::class.java)
    }

    @Test
    fun happyPath_thenSaveReserveSell(){
        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.just(Stock(id = "456", total = 10, productId = "123")))

        whenever(reserveRepository.findByIdAndStockId(anyString(), any()))
            .thenReturn(Mono.just(Reserve(id = "321",stockId = "456", sold = false)))

        whenever(reserveRepository.sell(any()))
            .thenReturn(Mono.just(Reserve(id = "321",stockId = "456", sold = false)))

        productService.sell("123", "321").block()

        verify(reserveRepository).sell(any())
        verify(stockRepository).adjustStockAfterSell(anyString())
    }
}

