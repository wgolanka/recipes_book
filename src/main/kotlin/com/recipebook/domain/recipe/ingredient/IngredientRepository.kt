package com.recipebook.domain.recipe.ingredient

import com.recipebook.domain.recipe.dto.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IngredientRepository : JpaRepository<Ingredient, Long> {
    fun getAllByIdIsNotNull(): List<Ingredient>
    fun findByIdIs(id: UUID): Ingredient?
    fun getIngredientByIdEquals(id: UUID?): Ingredient?
    fun deleteByIdEquals(id: UUID?)
}