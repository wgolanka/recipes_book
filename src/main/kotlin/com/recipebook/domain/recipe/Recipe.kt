package com.recipebook.domain.recipe

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToMany

@Entity
class Recipe(var title: String,
             var description: String,
             val authorId: UUID,

             @ElementCollection
             val ratingsIds: Set<String>,

             val measurementSystemId: Int,

             @ElementCollection
             val tagsIds: List<String>,

             val recipeImage: String,
             val isRecipePrivate: Boolean,

             @ElementCollection
             val steps: List<String>,

             @ElementCollection
             val commentsIds: List<UUID>) : AbstractJpaPersistable<Recipe>(), Serializable {

    @ManyToMany(fetch = FetchType.EAGER)
    val ingredients: List<Ingredient> = mutableListOf()

    fun addIngredient(ingredient: Ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.plus(ingredient)
            ingredient.addRecipe(this)
        }
    }
}