package com.recipebook.domain.recipe

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecipeRepository : JpaRepository<Recipe, Long> {
    fun getAllByIdIsNotNull(): List<Recipe>
    fun getRecipeByIdEquals(id: UUID?): Recipe?
    fun deleteByIdEquals(id: UUID?)
}