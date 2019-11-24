package com.recipebook.domain.recipe

import com.fasterxml.jackson.annotation.JsonIgnore
import com.recipebook.domain.user.BaseUser
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Recipe(var title: String,
             var description: String,

             @JsonIgnore
             @ManyToOne
             @JoinColumn(name = "user_id", nullable = false)
             var author: BaseUser) : AbstractJpaPersistable<Recipe>(), Serializable {


    init {
        author.addCreatedRecipe(this)
    }

    fun setRecipeAuthor(baseUser: BaseUser) {
        if (author == baseUser) {
            return
        }

        author.removeRecipe(this)

        author = baseUser
        author.addCreatedRecipe(this)
    }
}