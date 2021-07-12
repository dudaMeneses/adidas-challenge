package com.duda.adidaschallenge.integration

import com.duda.adidaschallenge.infrastructure.database.mongo.ProductMongoDBRepository
import com.duda.adidaschallenge.infrastructure.database.mongo.ReserveMongoDBRepository
import com.duda.adidaschallenge.infrastructure.database.mongo.StockMongoDBRepository
import com.duda.adidaschallenge.infrastructure.model.ProductMongo
import com.duda.adidaschallenge.infrastructure.model.StockMongo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@EnableAutoConfiguration(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
class FindProductStockIntegrationTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var productMongoDBRepository: ProductMongoDBRepository

    @Autowired
    private lateinit var stockMongoDBRepository: StockMongoDBRepository

    @Test
    fun whenProductNotFound_thenReturnNotFoundWithMessage(){
        client.get()
            .uri("/product/{id}", "999999999999")
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
            }
            .exchange()
            .expectStatus().isNotFound
            .expectBody().jsonPath(".message").isEqualTo("Stock not found for product 999999999999")
    }

    @Test
    fun whenProductExists_thenReturnProductStock(){
        val product = productMongoDBRepository.save(ProductMongo(name = "test")).block()

        product?.id?.let {
            StockMongo(productId = it, total = 10)
        }?.let {
            stockMongoDBRepository.save(it).block()
        }

        client.get()
            .uri("/product/{id}", product?.id)
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.IN_STOCK").isEqualTo(10)
            .jsonPath("$.RESERVED").isEqualTo(0)
            .jsonPath("$.SOLD").isEqualTo(0)
    }
}