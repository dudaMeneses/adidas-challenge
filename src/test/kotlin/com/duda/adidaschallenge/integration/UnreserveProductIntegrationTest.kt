package com.duda.adidaschallenge.integration

import com.duda.adidaschallenge.application.projection.request.ReservationRequest
import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.domain.model.Stock
import com.duda.adidaschallenge.infrastructure.database.ReserveRepository
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
class UnreserveProductIntegrationTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var productMongoDBRepository: ProductMongoDBRepository

    @Autowired
    private lateinit var stockRepository: StockRepository

    @Autowired
    private lateinit var reserveRepository: ReserveRepository

    @Test
    fun whenStockNotFound_thenReturnNotFound(){
        client.post()
            .uri("/product/{id}/unreserve", "999999999999")
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .bodyValue(ReservationRequest("123"))
            .exchange()
            .expectStatus().isNotFound
            .expectBody().jsonPath(".message").isEqualTo("Stock not found for product 999999999999")
    }

    @Test
    fun whenHasStockButReservationWasNotPlaced_thenReturnBadRequest(){
        val product = productMongoDBRepository.save(ProductMongo(name = "test")).block()
        stockRepository.save(Stock(productId = product?.id!!, total = 1)).block()

        client.post()
            .uri("/product/{id}/unreserve", product.id)
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .bodyValue(ReservationRequest("123"))
            .exchange()
            .expectStatus().isNotFound
            .expectBody().jsonPath(".message").isEqualTo("Reserve 123 not found for product ${product.id}")
    }

    @Test
    fun whenHappyPath_thenReturnOk(){
        val product = productMongoDBRepository.save(ProductMongo(name = "test")).block()
        val stock = stockRepository.save(Stock(productId = product?.id!!, total = 1)).block()
        val reserve = reserveRepository.save(Reserve(stockId = stock?.id!!)).block()

        client.post()
            .uri("/product/{id}/unreserve", product.id)
            .headers {
                it.accept = listOf(MediaType.APPLICATION_JSON)
                it.contentType = MediaType.APPLICATION_JSON
            }
            .bodyValue(ReservationRequest(reservationToken = reserve?.id!!))
            .exchange()
            .expectStatus().isOk
    }
}