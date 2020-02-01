package com.recipebook.domain.recipe

import com.recipebook.domain.recipe.comment.CommentRepository
import com.recipebook.domain.recipe.comment.RecipeRatingRepository
import com.recipebook.domain.recipe.dto.*
import com.recipebook.domain.recipe.ingredient.IngredientRepository
import com.recipebook.domain.recipe.measurmentunit.MeasurementUnitRepository
import com.recipebook.domain.recipe.tag.TagRepository
import com.recipebook.domain.user.AuthorService
import javassist.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class RecipeService(private val recipeRepository: RecipeRepository,
                    private val authorService: AuthorService,
                    private val measurementUnitRepository: MeasurementUnitRepository,
                    private val ingredientRepository: IngredientRepository,
                    private val tagRepository: TagRepository,
                    private val commentRepository: CommentRepository,
                    private val recipeRatingRepository: RecipeRatingRepository) {

    fun create(recipe: Recipe): Recipe? {
        val author = authorService.getById(recipe.authorId)
                ?: throw NotFoundException("Author with id ${recipe.authorId} doesn't exist")

        val recipeRating = RecipeRating()
        recipeRatingRepository.save(recipeRating)

        val newRecipe = Recipe(recipe.title,
                recipe.description,
                0.0,
                recipeRating,
                recipe.authorId,
                recipe.recipeImage ?: " ",
                recipe.recipePrivate,
                recipe.ingredients,
                recipe.steps,
                recipe.tagsIds,
                recipe.comments,
                LocalDate.now())

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

        recipeRating.recipe = newRecipe
        recipeRatingRepository.saveAndFlush(recipeRating)

        return recipeRepository.findByIdIs(newRecipe.getId()!!) ?: return null
    }

    fun create(comment: Comment): Comment? {
        val author = authorService.getById(comment.authorId) ?: return null
        val recipe = recipeRepository.getRecipeByIdEquals(comment.recipeId) ?: return null

        val newComment = Comment(
                author.getId()!!,
                comment.authorNickname,
                comment.recipeId,
                comment.commentContent,
                comment.recipeRating,
                comment.pictureLink)

        newComment.setNewRecipe(recipe)

        addRecipeRating(recipe, newComment)
        authorService.refreshRating(author.getId()!!)

        commentRepository.saveAndFlush(newComment)
        return commentRepository.findByIdIs(newComment.getId()!!) ?: return null
    }

    private fun addRecipeRating(recipe: Recipe, newComment: Comment) {
        recipe.ratingHistory!!.addNew(newComment.recipeRating ?: 0.0)
        recipe.rating = recipe.ratingHistory!!.getRating()
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
        return recipeRepository.getRecipeByIdEquals(recipeId) ?: return null
    }

    fun getIngredients(): List<Ingredient> {
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
        recipe.title = updated.title
        recipe.description = updated.description
        recipe.rating = updated.rating
        recipe.recipeImage = updated.recipeImage
        recipe.recipePrivate = updated.recipePrivate
        recipe.steps = updated.steps

        var counter = 0
        recipe.ingredients.forEach { ingredient ->
            if (updated.ingredients.size <= counter) {
                ingredient.measurementUnit.unit = updated.ingredients[counter].measurementUnit.unit

                measurementUnitRepository.saveAndFlush(ingredient.measurementUnit)

                ingredient.name = updated.ingredients[counter].name
                ingredient.quantity = updated.ingredients[counter].quantity

                ingredientRepository.saveAndFlush(ingredient)

                counter += 1
            }
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

    fun delete(recipeId: UUID) {
        val recipe = recipeRepository.findByIdIs(recipeId)
                ?: throw NotFoundException("Recipe with id $recipeId doesn't exist")
        recipe.author?.recipes?.remove(recipe)
        authorService.refreshRating(recipe.authorId)

        if (recipe.author!!.favoriteRecipes.contains(recipeId)) {
            recipe.author!!.favoriteRecipes.remove(recipeId)
        }

        recipe.author = null
        recipe.ingredients.forEach { ingredient ->
            ingredientRepository.delete(ingredient)
        }
        recipe.ingredients.clear()
        recipe.ratingHistory = null
        recipeRepository.saveAndFlush(recipe)
        recipeRepository.delete(recipe)

        if (recipeRepository.existsByIdIs(recipeId)) {
            throw Exception()
        }
    }

    fun search(authorId: UUID, phrase: String?, destination: String?, onlyMine: Boolean?, onlyPrivate: Boolean?,
               onlyFavorite: Boolean?): List<Recipe>? {

        val allRecipes = recipeRepository.findAll()
        val author = authorService.getById(authorId) ?: return null

        var allRecipesFiltered = listOf<Recipe>()

        if (destination != null && phrase != null) {

            if (destination.toLowerCase() == "title".toLowerCase()) {
                allRecipesFiltered = allRecipes.filter { recipe -> phrase.contains(recipe.title, ignoreCase = true) }
            }

            if (destination.toLowerCase() == "description".toLowerCase()) {
                allRecipesFiltered = allRecipes.filter { recipe -> phrase.contains(recipe.description, ignoreCase = true) }
            }

            if (destination.toLowerCase() == "tags".toLowerCase()) {
                allRecipesFiltered = allRecipes.filter { recipe ->
                    recipe.tagsIds.any { tag -> phrase.contains(tag.name, ignoreCase = true) }
                }
            }
        }

        if (onlyMine != null && onlyMine) {
            allRecipesFiltered = allRecipesFiltered.filter { recipe -> recipe.authorId == authorId }
        }

        if (onlyFavorite != null && onlyFavorite) {
            allRecipesFiltered = allRecipesFiltered.filter { recipe -> author.favoriteRecipes.contains(recipe.getId()) }
        }

        if (onlyPrivate != null && onlyPrivate) {
            allRecipesFiltered = allRecipesFiltered.filter { recipe -> recipe.recipePrivate }
        }

        return allRecipesFiltered
    }
}