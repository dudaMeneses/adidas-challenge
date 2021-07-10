package com.duda.adidaschallenge.application.projection.request

import io.swagger.annotations.ApiModelProperty

data class ProductRequest(@ApiModelProperty(example = "test product") val name: String)
