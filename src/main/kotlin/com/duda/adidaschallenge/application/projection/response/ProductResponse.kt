package com.duda.adidaschallenge.application.projection.response

import com.duda.adidaschallenge.domain.model.Product
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductResponse(
        @JsonProperty("IN_STOCK") val inStock: Int,
        @JsonProperty("RESERVED") val reserved: Int,
        @JsonProperty("SOLD") val sold: Int
) {
        fun of(product: Product): ProductResponse = ProductResponse(
                product.stock.total,
                product.stock.reserved,
                product.stock.sold
        )
}