package com.recipebook.domain.ingredient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.transaction.Transactional

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:8080/#"], maxAge = 3600)
@Controller
@Validated
@RequestMapping("/ingredient")
@Transactional
/* @Autowired => nie chce przyjąć */

class IngredientController(private val ingredientService: IngredientService) {

    @PostMapping(value = ["/add"])
    @ResponseStatus(OK)
    fun addIngredient(@RequestBody(required = false) ingredient: Ingredient, response: HttpServletResponse) {
        IngredientService.add(ingredient) //todo null?
    }

    @GetMapping("/all")
    fun getAllIngredients(): ResponseEntity<List<Ingredient>> {
        val all = ingredientService.getAll()
        return status(OK).body(all)
    }

    @PutMapping("/edit")
    @ResponseStatus(OK)
    fun updateIngredient(@RequestBody(required = false) IngredientObject: Ingredient) {
        ingredientService.update(IngredientObject)
    }

    @DeleteMapping("/delete")
    @ResponseStatus(OK)
    fun deleteIngredient(@RequestParam(required = true) teaId: UUID) {
        ingredientService.delete(teaId)
    }
}

