package com.recipebook.domain.recipe.comment

import com.recipebook.domain.recipe.dto.RecipeRating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecipeRatingRepository : JpaRepository<RecipeRating, Long> {
    fun getAllByIdIsNotNull(): List<RecipeRating>
    fun findByIdIs(id: UUID): RecipeRating?
    fun getRecipeRatingByIdEquals(id: UUID?): RecipeRating?
    fun deleteByIdEquals(id: UUID?)
}