package com.duda.adidaschallenge.domain.model

data class Stock(val id: String?, val total: Int, val reserves: List<Reserve>) {
    constructor(total: Int, reserves: List<Reserve>) : this(null, total, reserves)

    fun getReserved(): Int =
        reserves.filter { reserve -> !reserve.sold }.count()

    fun getSold(): Int =
        reserves.filter { reserve -> reserve.sold }.count()
}
