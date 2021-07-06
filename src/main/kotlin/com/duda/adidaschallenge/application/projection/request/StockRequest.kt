package com.duda.adidaschallenge.application.projection.request

import com.duda.adidaschallenge.domain.model.Stock

data class StockRequest(var stock: Int) {
    fun toModel(): Stock = Stock(stock)
}