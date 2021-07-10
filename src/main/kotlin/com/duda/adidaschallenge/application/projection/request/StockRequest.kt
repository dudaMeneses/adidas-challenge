package com.duda.adidaschallenge.application.projection.request

import io.swagger.annotations.ApiModelProperty

data class StockRequest(@ApiModelProperty(example = "1") var stock: Int)