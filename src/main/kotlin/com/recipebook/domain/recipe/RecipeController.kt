package com.recipebook.domain.recipe

import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.transaction.Transactional

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@Validated
@RequestMapping("/recipe")
@Transactional
class RecipeController(private val recipeService: RecipeService) {


    @PostMapping(value = ["/add"])
    @ResponseStatus(OK)
    fun addRecipe(@RequestBody(required = false) recipeObject: Recipe, response: HttpServletResponse) {
        recipeService.add(recipeObject) //todo null?
    }

    @GetMapping("/all")
    fun getAllRecipes(): ResponseEntity<List<Recipe>> {

        val all = recipeService.getAll()
        return status(OK).body(all)
    }

    @PutMapping("/edit")
    @ResponseStatus(OK)
    fun getEditTea(@RequestBody(required = false) recipeObject: Recipe) {

        recipeService.update(recipeObject)
    }

    @DeleteMapping("/delete")
    @ResponseStatus(OK)
    fun deleteTea(@RequestParam(required = true) teaId: UUID) {

        recipeService.delete(teaId)
    }
}

