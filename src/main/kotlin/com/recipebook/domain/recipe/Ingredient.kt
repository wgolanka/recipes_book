package com.recipebook.domain.recipe

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class Ingredient(val name: String, val unit: String, val quantity: Double) : AbstractJpaPersistable<Recipe>(),
        Serializable {

    @ManyToMany
    val recipes: List<Recipe> = mutableListOf()

    fun addRecipe(recipe: Recipe) {
        if (!recipes.contains(recipe)) {
            recipes.plus(recipe)
            recipe.addIngredient(this)
        }
    }
}

