package com.recipebook.domain.ingredient

import com.recipebook.domain.recipe.Recipe
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.ManyToMany

@Entity
class Ingredient(var name: String, var unit: String, var quantity: Double) : AbstractJpaPersistable<Ingredient>(),
        Serializable {

    @ManyToMany
    val ingredients = mutableListOf<Recipe>()

        fun addRecipe(ingredient: Recipe) {
            if (!ingredients.contains(ingredient)) {
        }
    }
}

