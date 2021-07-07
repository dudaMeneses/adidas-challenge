package com.duda.adidaschallenge.infrastructure.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "reserves")
class ReserveMongo(val id: String?, val token: String, val sold: Boolean)
