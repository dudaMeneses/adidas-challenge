package com.duda.adidaschallenge.domain.model

data class Stock(val id: String? = null,
                 val total: Int,
                 val productId: String,
                 val reserves: List<Reserve> = listOf()) {

    fun getReserved(): Int =
        reserves.filter { reserve -> !reserve.sold }.count()

    fun getSold(): Int =
        reserves.filter { reserve -> reserve.sold }.count()
}
