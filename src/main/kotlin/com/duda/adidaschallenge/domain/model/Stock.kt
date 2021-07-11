package com.duda.adidaschallenge.domain.model

data class Stock(val id: String? = null,
                 val total: Int = 0,
                 val productId: String,
                 val reserves: List<Reserve> = listOf()) {

    fun getSold(): Int =
        reserves.filter { reserve -> reserve.sold }.count()
}
