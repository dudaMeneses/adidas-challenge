package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono

class StockServiceFindByProductIdTest {

    private lateinit var stockService: StockServiceImpl
    private val stockRepository: StockRepository = mock()

    @BeforeEach
    fun init(){
        stockService = StockServiceImpl(mock(), stockRepository)
    }

    @Test
    fun whenHappyPath_thenCallRepository() {
        whenever(stockRepository.findByProductId(anyString()))
            .thenReturn(Mono.empty())

        stockService.findByProductId("132").block()
        verify(stockRepository).findByProductId("132")
    }
}