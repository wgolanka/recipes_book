package com.recipebook.domain.recipe.dto

import java.util.*
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Comment(val authorId : UUID,
              val authorNickname : String,
              var recipeId : UUID?,
              val commentContent: String,
              var recipeRating: Double?) {

    @ManyToOne
    var recipe : Recipe? = null

    fun setNewRecipe(givenRecipe: Recipe){
        if(recipe == null){
            recipe = givenRecipe
            recipeId = givenRecipe.getId()
        }
    }
}