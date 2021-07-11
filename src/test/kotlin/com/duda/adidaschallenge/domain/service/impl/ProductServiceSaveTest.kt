package com.duda.adidaschallenge.domain.service.impl

import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.service.ProductService
import com.duda.adidaschallenge.infrastructure.database.ProductRepository
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
import com.duda.adidaschallenge.infrastructure.database.StockRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProductServiceSaveTest{

    @InjectMocks
    private lateinit var productService: ProductServiceImpl

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var stockRepository: StockRepository

    @Mock
    private lateinit var reserveRepository: ReserveRepository

    @Test
    fun whenHappyPath_thenSaveProduct(){
        productService.save(Product(name = "test"))
        verify(productRepository).save(Product(name = "test"))
    }
}