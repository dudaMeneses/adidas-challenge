package com.duda.adidaschallenge.integration

import com.duda.adidaschallenge.application.projection.request.ProductRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CreateProductIntegrationTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun whenHappyPath_thenCreateProduct(){
        client.post()
            .uri("/product")
            .bodyValue(ProductRequest("test"))
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .exchange()
            .expectStatus().isCreated
            .expectBody().jsonPath("$.id").isNotEmpty
    }

    @Test
    fun whenEmptyBody_thenCreateProduct(){
        client.post()
            .uri("/product")
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .exchange()
            .expectStatus().isBadRequest
    }
}