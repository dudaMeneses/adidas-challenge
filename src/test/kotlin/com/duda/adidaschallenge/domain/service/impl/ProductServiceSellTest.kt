package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.service.exception.StockNotFoundForProductException
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class ProductServiceSellTest {
    @InjectMocks
    private lateinit var productService: ProductServiceImpl

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var stockRepository: StockRepository

    @Mock
    private lateinit var reserveRepository: ReserveRepository

    @Test
    fun whenStockNotFound_thenStockNotFoundForProductException(){
        doReturn(Mono.error<Throwable>(StockNotFoundForProductException("123") as Throwable))
            .`when`(stockRepository)
            .findByProductId(anyString())

        StepVerifier.create(productService.sell("123", "321"))
            .expectError(StockNotFoundForProductException::class.java)
    }
}

