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
import org.mockito.kotlin.mock

@ExtendWith(MockitoExtension::class)
class ProductServiceSaveTest{

    private lateinit var productService: ProductServiceImpl
    private val productRepository: ProductRepository = mock()

    @BeforeEach
    fun init(){
        productService = ProductServiceImpl(productRepository, mock(), mock())
    }

    @Test
    fun whenHappyPath_thenSaveProduct(){
        productService.save(Product(name = "test"))
        verify(productRepository).save(Product(name = "test"))
    }
}