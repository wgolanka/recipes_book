package com.recipebook.domain.ingredient

import com.recipebook.domain.recipe.Recipe
import com.recipebook.domain.user.Author
import org.springframework.stereotype.Service
import java.util.*

class IngredientService (private val ingredientRepository: IngredientRepository) {

    fun add(ingredient: Ingredient) {
        val newIngredient = Ingredient(ingredient.name, ingredient.unit, ingredient.quantity)

        ingredientRepository.saveAndFlush(
                Ingredient(ingredient.name,
                            ingredient.unit,
                            ingredient.quantity))
    }

    fun getAll(): List<Ingredient> {
        return ingredientRepository.getAllByIdIsNotNull()
    }

    fun update(ingredient: Ingredient) {
        if (ingredient.getId() == null) {
            return //TODO throw exception
    }
        val existingIngredient = ingredientRepository.getIngredientByIdEquals(ingredient.getId())
        if (existingIngredient != null) {
        ingredientRepository.save(updateIngredientFields(existingIngredient, ingredient))
    }
}

    fun updateIngredientFields(ingredient: Ingredient, updated: Ingredient): Ingredient {
        ingredient.name = updated.name
        ingredient.unit = updated.unit
        ingredient.quantity = updated.quantity

    return ingredient
}

    fun delete(teaId: UUID) {
        val tea = ingredientRepository.getIngredientByIdEquals(teaId) ?: return
        ingredientRepository.delete(tea) //TODO does it work?
    }
    fun getIngredients(name: String): List<Ingredient> {
        val allIngredients = getAll()
                .filter { ingredient -> ingredient.name.contains(name)  }
        return allIngredients
    }
}
