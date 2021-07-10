package com.duda.adidaschallenge.application.projection.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty

data class StockResponse(
        @ApiModelProperty(example = "5") @JsonProperty("IN_STOCK") val inStock: Int,
        @ApiModelProperty(example = "2") @JsonProperty("RESERVED") val reserved: Int,
        @ApiModelProperty(example = "1") @JsonProperty("SOLD") val sold: Int
)