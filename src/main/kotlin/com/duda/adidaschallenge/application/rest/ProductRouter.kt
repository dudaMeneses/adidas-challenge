package com.duda.adidaschallenge.application.rest

import com.duda.adidaschallenge.application.rest.handler.ProductHandler
import com.duda.adidaschallenge.application.rest.handler.ReserveHandler
import com.duda.adidaschallenge.application.rest.handler.StockHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router


@Configuration
class ProductRouter {

    @Bean
    fun route(@Autowired stockHandler: StockHandler,
              @Autowired reserveHandler: ReserveHandler,
              @Autowired productHandler: ProductHandler): RouterFunction<ServerResponse> = router {
        (accept(MediaType.APPLICATION_JSON) and "/product").nest {
            POST("/", productHandler::create)
            GET("{id}", stockHandler::findByProductId)
            PATCH("{id}/stock", stockHandler::register)
            POST("{id}/reserve", reserveHandler::reserve)
            POST("{id}/unreserve", reserveHandler::unreserve)
            POST("{id}/sold", productHandler::sell)
        }
    }
}