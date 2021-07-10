package com.duda.adidaschallenge.infrastructure.database

import com.duda.adidaschallenge.domain.model.Reserve
import com.duda.adidaschallenge.infrastructure.database.mongo.ReserveMongoDBRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class ReserveRepository(private val reserveMongoDBRepository: ReserveMongoDBRepository) {

    fun findByStockId(stockId: String?): Flux<Reserve> =
        reserveMongoDBRepository.findByStockId(stockId)
            .map { reserve -> Reserve(reserve.id, reserve.sold) }

    fun unreserve(token: String) {
        TODO("Not yet implemented")
    }

}
