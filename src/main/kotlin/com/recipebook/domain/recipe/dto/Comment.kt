package com.recipebook.domain.recipe.dto

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Comment(
        @Column(name = "notAuthorId") //I will have nightmares after this school project
        val authorId: UUID,

        val authorNickname: String,

        @Column(name = "notRecipeId")
        var recipeId: UUID?,

        val commentContent: String,

        var recipeRating: Double?,
        var pictureLink: String) : AbstractJpaPersistable<Comment>(), Serializable {

    @ManyToOne
    var recipe: Recipe? = null

    fun setNewRecipe(givenRecipe: Recipe) {
        if (recipe == null) {
            recipe = givenRecipe
            recipeId = givenRecipe.getId()
        }
    }
}