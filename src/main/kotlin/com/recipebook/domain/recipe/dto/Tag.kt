package com.recipebook.domain.recipe.dto

import com.fasterxml.jackson.annotation.JsonBackReference
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Tag(val name: String) : AbstractJpaPersistable<Tag>(), Serializable {

    @JsonBackReference
    @ManyToOne
    var recipe: Recipe? = null

    fun setNewRecipe(givenRecipe: Recipe) {
        if (recipe == null) {
            recipe = givenRecipe
        }
    }
}