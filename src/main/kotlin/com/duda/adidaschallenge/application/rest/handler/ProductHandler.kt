package com.duda.adidaschallenge.application.rest.handler

import com.duda.adidaschallenge.application.projection.response.ProductResponse
import com.duda.adidaschallenge.application.projection.request.ProductRequest
import com.duda.adidaschallenge.domain.model.Product
import com.duda.adidaschallenge.domain.service.ProductService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ProductHandler(private val productService: ProductService) {
    fun sell(id: String, reservationToken: String) =
        productService.sell(id, reservationToken)

    fun create(request: ProductRequest): Mono<ProductResponse> =
            productService.save(request.toModel())
            .map { it.toResponse() }

    fun Product.toResponse(): ProductResponse = ProductResponse(this.id, this.name)
    fun ProductRequest.toModel(): Product = Product(name = this.name)

}
