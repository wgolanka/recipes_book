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
class Author(val nickname: String,
             val nicknameColorId: Int,
             val threshold: String,
             val avatarImg: String,
             val createdCommentsIds: IntArray,
             val createdRecipesIds: IntArray,
             val createdRatingsIds: IntArray,
             val isAccountActive: Boolean) : AbstractJpaPersistable<Author>(), Serializable {

    fun addCreatedRecipe(recipe: Recipe) {
//        if (!createdRecipes.contains(recipe)) {
//            createdRecipes.add(recipe)
//
//            recipe.setRecipeAuthor(this)
//        }
    }

    fun removeRecipe(recipe: Recipe) {
//        createdRecipes.remove(recipe)
    }
}
