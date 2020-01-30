package com.recipebook.domain.recipe

import com.recipebook.domain.recipe.dto.Ingredient
import com.recipebook.domain.recipe.dto.MeasurementUnit
import com.recipebook.domain.recipe.dto.Recipe
import com.recipebook.domain.recipe.dto.SkimRecipe
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

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:8080/#"], maxAge = 3600)
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
                            recipe.recipeImage)
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
        return status(OK).body(recipeService.geIngredients())
    }

    @GetMapping("/measurementUnits")
    fun getMeasurementUnits(): ResponseEntity<List<MeasurementUnit>> {
        return status(OK).body(recipeService.getMeasurementUnits())
    }

    @GetMapping("/{recipeId}")
    fun getRecipe(@PathVariable("recipeId") recipeId: UUID): ResponseEntity<Recipe> {
        return status(OK).body(recipeService.get(recipeId))
    }
//
//    @PutMapping("/edit")
//    @ResponseStatus(OK)
//    fun updateRecipe(@RequestBody(required = false) recipeObject: Recipe) {
//        recipeService.update(recipeObject)
//    }
//
//    @DeleteMapping("/delete")
//    @ResponseStatus(OK)
//    fun deleteRecipe(@RequestParam(required = true) teaId: UUID) {
//        recipeService.delete(teaId)
//    }
}

