package com.recipebook.domain.recipe

import com.recipebook.domain.recipe.comment.CommentRepository
import com.recipebook.domain.recipe.dto.*
import com.recipebook.domain.recipe.ingredient.IngredientRepository
import com.recipebook.domain.recipe.measurmentunit.MeasurementUnitRepository
import com.recipebook.domain.recipe.tag.TagRepository
import com.recipebook.domain.user.AuthorService
import javassist.NotFoundException
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class RecipeService(private val recipeRepository: RecipeRepository,
                    private val authorService: AuthorService,
                    private val measurementUnitRepository: MeasurementUnitRepository,
                    private val ingredientRepository: IngredientRepository,
                    private val tagRepository: TagRepository,
                    private val commentRepository: CommentRepository) {

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

    fun create(comment: Comment): Comment? {
        val author = authorService.getById(comment.authorId)
        val recipe = recipeRepository.getRecipeByIdEquals(comment.recipeId) ?: return null

        val newComment = Comment(
                author.getId()!!,
                comment.authorNickname,
                comment.recipeId,
                comment.commentContent,
                comment.recipeRating,
                comment.pictureLink)

        newComment.setNewRecipe(recipe)

        commentRepository.saveAndFlush(newComment)
        return commentRepository.findByIdIs(newComment.getId()!!) ?: return null
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

    fun getComments(): List<Comment> {
        return commentRepository.findAll()
    }

    fun updateAndGet(recipe: Recipe): Recipe {
        val existingRecipe = recipeRepository.getRecipeByIdEquals(recipe.getId())
                ?: throw NotFoundException("Recipe with id ${recipe.getId()} doesn't exist")

        updateAndSaveRecipeFields(existingRecipe, recipe)
        return existingRecipe
    }

    fun updateAndSaveRecipeFields(recipe: Recipe, updated: Recipe): Recipe {
        recipe.description = updated.description
        recipe.rating = updated.rating
        recipe.recipeImage = updated.recipeImage
        recipe.recipePrivate = updated.recipePrivate
        recipe.steps = updated.steps

        var counter = 0
        recipe.ingredients.forEach { ingredient ->
            ingredient.measurementUnit.unit = updated.ingredients[counter].measurementUnit.unit

            measurementUnitRepository.saveAndFlush(ingredient.measurementUnit)

            ingredient.name = updated.ingredients[counter].name
            ingredient.quantity = updated.ingredients[counter].quantity

            ingredientRepository.saveAndFlush(ingredient)

            counter += 1
        }

        recipe.tagsIds.forEach { tag ->
            tag.name = updated.tagsIds.find { updatedTag -> tag.getId() == updatedTag.getId() }?.name ?: tag.name
            tagRepository.save(tag)
        }

        recipe.comments.forEach { comment ->
            val updatedComment = updated.comments.find { updatedComment -> comment.getId() == updatedComment.getId() }

            comment.commentContent = updatedComment?.commentContent ?: comment.commentContent
            comment.recipeRating = updatedComment?.recipeRating ?: comment.recipeRating
            comment.pictureLink = updatedComment?.pictureLink ?: comment.pictureLink

            commentRepository.save(comment)
        }

        recipeRepository.saveAndFlush(recipe)
        return recipe
    }
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