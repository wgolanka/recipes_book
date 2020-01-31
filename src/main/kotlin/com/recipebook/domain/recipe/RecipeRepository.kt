package com.recipebook.domain.recipe

import com.recipebook.domain.recipe.dto.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecipeRepository : JpaRepository<Recipe, Long> {
    fun getAllByIdIsNotNull(): List<Recipe>
    fun findByIdIs(id: UUID): Recipe?
    fun getRecipeByIdEquals(id: UUID?): Recipe?
    fun existsByIdIs(id: UUID): Boolean
    fun deleteByIdEquals(id: UUID?)
}