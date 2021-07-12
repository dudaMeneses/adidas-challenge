package com.duda.adidaschallenge.integration

import com.duda.adidaschallenge.application.projection.request.StockRequest
import com.duda.adidaschallenge.infrastructure.database.mongo.ProductMongoDBRepository
import com.duda.adidaschallenge.infrastructure.model.ProductMongo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CreateStockForProductIntegrationTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var productMongoDBRepository: ProductMongoDBRepository

    @Test
    fun whenProductNotExists_thenReturnNotFound(){
        client.patch()
            .uri("/product/{id}/stock", "999999999999")
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .bodyValue(StockRequest(10))
            .exchange()
            .expectStatus().isNotFound
            .expectBody().jsonPath(".message").isEqualTo("Product 999999999999 not found")
    }

    @Test
    fun whenProductExistsAndThereIsNoStock_thenSaveAccordingly(){
        val product = productMongoDBRepository.save(ProductMongo(name = "test")).block()

        client.patch()
            .uri("/product/{id}/stock", product?.id)
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .bodyValue(StockRequest(10))
            .exchange()
            .expectStatus().isNoContent

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