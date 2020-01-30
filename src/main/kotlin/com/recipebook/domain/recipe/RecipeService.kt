package com.recipebook.domain.recipe

import com.recipebook.domain.recipe.dto.Ingredient
import com.recipebook.domain.recipe.dto.MeasurementUnit
import com.recipebook.domain.recipe.dto.Recipe
import com.recipebook.domain.recipe.dto.Tag
import com.recipebook.domain.recipe.ingredient.IngredientRepository
import com.recipebook.domain.recipe.measurmentunit.MeasurementUnitRepository
import com.recipebook.domain.recipe.tag.TagRepository
import com.recipebook.domain.user.AuthorService
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class RecipeService(private val recipeRepository: RecipeRepository,
                    private val authorService: AuthorService,
                    private val measurementUnitRepository: MeasurementUnitRepository,
                    private val ingredientRepository: IngredientRepository,
                    private val tagRepository: TagRepository) {

    fun create(recipe: Recipe): Recipe? {
        val author = authorService.getById(recipe.authorId)
        //todo check if author exist
        val newRecipe = Recipe(recipe.title,
                recipe.description,
                recipe.rating,
                recipe.authorId,
                recipe.recipeImage,
                recipe.recipePrivate,
                recipe.ingredients,
                recipe.steps,
                recipe.tagsIds,
                recipe.comments)

        newRecipe.author = author

        val ingredients: MutableList<Ingredient> = mutableListOf()
        recipe.ingredients.forEach { ingredient ->
            val newIngredient = Ingredient(ingredient.name, ingredient.quantity, createAndGet(ingredient.measurementUnit))
            ingredientRepository.saveAndFlush(newIngredient)
            ingredients.add(newIngredient)
        }
        newRecipe.ingredients = ingredients

        val tags: MutableSet<Tag> = mutableSetOf()
        recipe.tagsIds.forEach { tag ->
            val newTag = Tag(tag.name)
            tagRepository.saveAndFlush(newTag)
            tags.add(newTag)
        }
        newRecipe.tagsIds = tags

        recipeRepository.saveAndFlush(newRecipe)

        tags.forEach { tag ->
            tag.recipe = newRecipe
            tagRepository.saveAndFlush(tag)
        }

        return recipeRepository.findByIdIs(newRecipe.getId()!!) ?: return null
    }

    private fun createAndGet(measurementUnit: MeasurementUnit): MeasurementUnit {
        val newMeasurementUnit = MeasurementUnit(measurementUnit.unit)
        measurementUnitRepository.saveAndFlush(newMeasurementUnit)
        return newMeasurementUnit
    }

    fun getRecipes(): List<Recipe> {
        return recipeRepository.findAll()
    }

    fun get(recipeId: UUID): Recipe? {
        //todo throw 404 if not exist
        return recipeRepository.getRecipeByIdEquals(recipeId)
    }

    fun geIngredients(): List<Ingredient> {
        return ingredientRepository.findAll()
    }

    fun getMeasurementUnits(): List<MeasurementUnit> {
        return measurementUnitRepository.findAll()
    }

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