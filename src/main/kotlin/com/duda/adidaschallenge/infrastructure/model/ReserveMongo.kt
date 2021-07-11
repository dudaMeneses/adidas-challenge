package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "reserves")
data class ReserveMongo(val id: String? = null,
                        val stockId: String?,
                        val sold: Boolean = false,
                        @Version var version: Long? = null)

