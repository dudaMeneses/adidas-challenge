package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "reserves")
data class ReserveMongo(@Id var id: String? = UUID.randomUUID().toString(),
                        val stockId: String,
                        val sold: Boolean = false)

