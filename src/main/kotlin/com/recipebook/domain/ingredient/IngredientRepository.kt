package com.recipebook.domain.ingredient

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IngredientRepository : JpaRepository<Ingredient, Long> {
    fun getAllByIdIsNotNull(): List<Ingredient>
    fun getIngredientByIdEquals(id: UUID?): Ingredient?
    fun deleteByIdEquals(id: UUID?)
}