package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reactor.core.publisher.Flux

class ReserveServiceFindByStockIdTest {
    private lateinit var reserveService: ReserveServiceImpl
    private val reserveRepository: ReserveRepository = mock()

    @BeforeEach
    fun init(){
        reserveService = ReserveServiceImpl(reserveRepository, mock())
    }

    @Test
    fun whenHappyPath_thenCallRepository(){
        whenever(reserveRepository.findByStockId(anyString()))
            .thenReturn(Flux.empty())

        reserveService.findByStockId("456").blockFirst()

        verify(reserveRepository).findByStockId("456")
    }
}