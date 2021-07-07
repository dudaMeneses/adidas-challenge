package com.duda.adidaschallenge.application.projection.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductResponse(
        @JsonProperty("IN_STOCK") val inStock: Int,
        @JsonProperty("RESERVED") val reserved: Int,
        @JsonProperty("SOLD") val sold: Int
)