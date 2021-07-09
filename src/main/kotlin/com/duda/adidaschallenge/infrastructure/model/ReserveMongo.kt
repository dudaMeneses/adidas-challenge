package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "reserves")
data class ReserveMongo(val id: String?, val sold: Boolean)
