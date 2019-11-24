package com.recipebook.domain.recipe

import com.recipebook.domain.user.UserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecipeService(private val userService: UserService,
                    private val recipeRepository: RecipeRepository) {

    fun add(recipe: Recipe) {
        val user = userService.getCurrentUser()
        recipeRepository.saveAndFlush(
                Recipe(recipe.title, recipe.description, user)
        )
    }

    fun getAll(): List<Recipe> {
        return recipeRepository.getAllByIdIsNotNull()
    }

    fun update(recipe: Recipe) {
        if (recipe.getId() == null) {
            return //TODO throw exception
        }
        val existingRecipe = recipeRepository.getRecipeByIdEquals(recipe.getId())
        if (existingRecipe != null) {
            recipeRepository.save(updateRecipeFields(existingRecipe, recipe))
        }
    }

    fun updateRecipeFields(recipe: Recipe, updated: Recipe): Recipe {
        recipe.title = updated.title
        recipe.description = updated.description
        return recipe
    }

    fun delete(teaId: UUID) {
        val tea = recipeRepository.getRecipeByIdEquals(teaId) ?: return
        recipeRepository.delete(tea) //TODO does it work?
    }
}