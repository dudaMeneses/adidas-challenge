package com.duda.adidaschallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class AdidasChallengeApplication

fun main(args: Array<String>) {
	runApplication<AdidasChallengeApplication>(*args)
}
