package com.recipebook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.recipebook"])
class RecipesBookApplication

fun main(args: Array<String>) {
    runApplication<RecipesBookApplication>(*args)
}
