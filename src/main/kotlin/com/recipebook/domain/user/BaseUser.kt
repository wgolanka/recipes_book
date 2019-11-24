package com.recipebook.domain.user

import com.fasterxml.jackson.annotation.JsonBackReference
import com.recipebook.domain.recipe.Recipe
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class BaseUser(var nickname: String,
               var avatar: ByteArray?,
               val accountCreated: LocalDate,
               val description: String?,
               var emailAddress: String?) : AbstractJpaPersistable<BaseUser>(), Serializable {

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
    val createdRecipes: MutableSet<Recipe> = mutableSetOf()

    fun addCreatedRecipe(recipe: Recipe) {
        if (!createdRecipes.contains(recipe)) {
            createdRecipes.add(recipe)

            recipe.setRecipeAuthor(this)
        }
    }

    fun removeRecipe(recipe: Recipe) {
        createdRecipes.remove(recipe)
    }
}
