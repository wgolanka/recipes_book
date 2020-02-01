package com.recipebook.domain.user

import com.fasterxml.jackson.annotation.JsonBackReference
import com.recipebook.domain.recipe.dto.Recipe
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
class Author(var nickname: String,
             var nicknameColorId: Int,
             var password: String,
             var authorRating: Double,
             var email: String,
             var threshold: Int,
             var accountActive: Boolean) : AbstractJpaPersistable<Author>(), Serializable {

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
    val recipes: MutableSet<Recipe> = mutableSetOf()

    @ElementCollection
    var favoriteRecipes: MutableSet<UUID> = mutableSetOf()
}
