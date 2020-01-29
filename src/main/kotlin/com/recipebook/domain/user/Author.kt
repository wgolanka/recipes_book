package com.recipebook.domain.user

import com.recipebook.domain.recipe.dto.Recipe
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class Author(var nickname: String,
             var nicknameColorId: Int,
             var password: String,
             var authorRating: Double,
             var authorRatingSum: Double,
             var email: String,
             var threshold: Int,
             var accountActive: Boolean) : AbstractJpaPersistable<Author>(), Serializable {

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
    val recipes: MutableSet<Recipe> = mutableSetOf()
}
