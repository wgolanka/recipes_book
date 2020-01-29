package com.recipebook.domain.recipe

import com.recipebook.domain.recipe.dto.Recipe
import com.recipebook.domain.user.Author
import com.recipebook.domain.user.AuthorService
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecipeService(private val authorService: AuthorService,
                    private val recipeRepository: RecipeRepository) {

//    fun add(recipe: Recipe) {
//        val user = authorService.getCurrentUser()
//        val newRecipe = Recipe(recipe.title,
//                recipe.description,
//                user.getId()!!,
//                recipe.ratingsIds,
//                recipe.measurementSystemId,
//                recipe.tagsIds,
//                recipe.recipeImage,
//                recipe.isRecipePrivate,
//                recipe.steps,
//                recipe.commentsIds)
//
//        recipe.ingredients.stream().forEach { it.addRecipe(newRecipe) }
//
//        recipeRepository.saveAndFlush(
//                Recipe(recipe.title,
//                        recipe.description,
//                        user.getId()!!,
//                        recipe.ratingsIds,
//                        recipe.measurementSystemId,
//                        recipe.tagsIds,
//                        recipe.recipeImage,
//                        recipe.isRecipePrivate,
//                        recipe.steps,
//                        recipe.commentsIds)
//        )
//    }
//
//    fun getAll(): List<Recipe> {
//        return recipeRepository.getAllByIdIsNotNull()
//    }
//
//    fun update(recipe: Recipe) {
//        if (recipe.getId() == null) {
//            return //TODO throw exception
//        }
//        val existingRecipe = recipeRepository.getRecipeByIdEquals(recipe.getId())
//        if (existingRecipe != null) {
//            recipeRepository.save(updateRecipeFields(existingRecipe, recipe))
//        }
//    }
//
//    fun updateRecipeFields(recipe: Recipe, updated: Recipe): Recipe {
//        recipe.title = updated.title
//        recipe.description = updated.description
//        return recipe
//    }
//
//    fun delete(teaId: UUID) {
//        val tea = recipeRepository.getRecipeByIdEquals(teaId) ?: return
//        recipeRepository.delete(tea) //TODO does it work?
//    }
//
//    fun getUserRecipes(author: Author): List<Recipe> {
//        val allRecipes = getAll()
//        return allRecipes
//                .filter { recipe -> author.createdRecipesIds.contains(recipe.getId())}
//    }
}