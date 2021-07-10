package com.duda.adidaschallenge.application.rest

import com.duda.adidaschallenge.application.rest.handler.ProductHandler
import com.duda.adidaschallenge.application.rest.handler.ReserveHandler
import com.duda.adidaschallenge.application.rest.handler.StockHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router


@Configuration
class ProductRoute(private val stockHandler: StockHandler,
                   private val reserveHandler: ReserveHandler,
                   private val productHandler: ProductHandler) {
    @Bean
    fun routes() = router {
        (accept(MediaType.APPLICATION_JSON) and "/product").nest {
            "/{id}".nest {
                GET("/", stockHandler::findByProductId)
                PATCH("/stock", stockHandler::register)
                POST("/reserve", reserveHandler::reserve)
                POST("/unreserve", reserveHandler::unreserve)
                POST("/sold", productHandler::sell)
            }
        }
    }
}