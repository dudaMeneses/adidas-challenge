package com.duda.adidaschallenge.integration

import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.infrastructure.database.StockRepository
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
class ReserveProductIntegrationTest {
    @Autowired
    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var productMongoDBRepository: ProductMongoDBRepository

    @Autowired
    private lateinit var stockRepository: StockRepository

    @Test
    fun whenStockNotFound_thenReturnNotFound(){
        client.post()
            .uri("/product/{id}/reserve", "999999999999")
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .exchange()
            .expectStatus().isNotFound
            .expectBody().jsonPath(".message").isEqualTo("Stock not found for product 999999999999")
    }

    @Test
    fun whenHappyPath_thenReturnOk(){
        val product = productMongoDBRepository.save(ProductMongo(name = "test")).block()
        stockRepository.save(Stock(productId = product?.id!!, total = 1)).block()

        client.post()
            .uri("/product/{id}/reserve", product.id)
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .exchange()
            .expectStatus().isOk
            .expectBody().jsonPath(".reservationToken").isNotEmpty
    }

    @Test
    fun whenReservationsReachedLimit_thenReturnBadRequest(){
        val product = productMongoDBRepository.save(ProductMongo(name = "test")).block()
        stockRepository.save(Stock(productId = product?.id!!, total = 1)).block()

        client.post()
            .uri("/product/{id}/reserve", product.id)
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .exchange()
            .expectStatus().isOk
            .expectBody().jsonPath(".reservationToken").isNotEmpty

        client.post()
            .uri("/product/{id}/reserve", product.id)
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().jsonPath(".message").isEqualTo("Reserve quantity already reach stock limit")
    }
}