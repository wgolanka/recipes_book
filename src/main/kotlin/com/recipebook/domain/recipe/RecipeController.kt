package com.recipebook.domain.recipe

import com.recipebook.domain.recipe.dto.*
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.transaction.Transactional

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@Validated
@RequestMapping("/recipes")
@Transactional
class RecipeController(private val recipeService: RecipeService) {

    @PostMapping
    fun addRecipe(@RequestBody(required = false) recipe: Recipe): ResponseEntity<Recipe> {

        val newRecipe = recipeService.create(recipe)
                ?: return status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)

        return ok(newRecipe)
    }

    @PostMapping("/comments")
    fun addComment(@RequestBody(required = false) comment: Comment): ResponseEntity<Comment> {

        val newComment = recipeService.create(comment)
                ?: return status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)

        return ok(newComment)
    }

    @GetMapping
    fun getRecipes(): ResponseEntity<MutableSet<SkimRecipe>> {
        val allRecipes = recipeService.getRecipes()
        val skimmedRecipes = mutableSetOf<SkimRecipe>()
        allRecipes.forEach { recipe ->
            skimmedRecipes.add(
                    SkimRecipe(recipe.getId(),
                            recipe.title,
                            recipe.author?.nickname,
                            recipe.author?.nicknameColorId,
                            recipe.rating,
                            recipe.recipeImage,
                            recipe.createdDate)
            )
        }
        return status(OK).body(skimmedRecipes)
    }

    @GetMapping("/details")
    fun getRecipesDetailed(): ResponseEntity<List<Recipe>> {
        return status(OK).body(recipeService.getRecipes())
    }

    @GetMapping("/ingredients")
    fun getIngredients(): ResponseEntity<List<Ingredient>> {
        return status(OK).body(recipeService.getIngredients())
    }

    @GetMapping("/measurementUnits")
    fun getMeasurementUnits(): ResponseEntity<List<MeasurementUnit>> {
        return status(OK).body(recipeService.getMeasurementUnits())
    }

    @GetMapping("/{recipeId}")
    fun getRecipe(@PathVariable("recipeId") recipeId: UUID): ResponseEntity<Recipe> {
        val recipe = recipeService.get(recipeId) ?: return status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        return ok(recipe)
    }

    @GetMapping("/search/{authorId}")
    fun search(@PathVariable("authorId") authorId: UUID,
               @RequestParam(required = false) phrase: String?,
               @RequestParam(required = false) destination: String?,
               @RequestParam(required = false) onlyMine: Boolean?,
               @RequestParam(required = false) onlyPrivate: Boolean?,
               @RequestParam(required = false) onlyFavorite: Boolean?): ResponseEntity<List<Recipe>> {

        val recipes = recipeService.search(authorId, phrase, destination, onlyMine, onlyPrivate, onlyFavorite)
                ?: return status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)

        return ok(recipes)
    }

    @GetMapping("/comments")
    fun getComments(): ResponseEntity<List<Comment>> {
        return status(OK).body(recipeService.getComments())
    }

    @PutMapping
    fun updateRecipe(@RequestBody(required = false) recipe: Recipe): ResponseEntity<Recipe> {
        if (recipe.getId() == null) {
            return status(HttpStatus.BAD_REQUEST).body(null)
        }
        return status(OK).body(recipeService.updateAndGet(recipe))
    }

    @ResponseStatus(OK)
    @DeleteMapping("/{recipeId}")
    fun deleteRecipe(@PathVariable("recipeId") recipeId: UUID) {
        recipeService.delete(recipeId)
    }
}

