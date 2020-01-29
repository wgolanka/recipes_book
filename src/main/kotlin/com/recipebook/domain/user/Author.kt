package com.recipebook.domain.user

import com.recipebook.domain.recipe.dto.Recipe
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.*

@Entity
class Author(val nickname: String,
             val nicknameColorId: Int,
             val password: String,
             val authorRating: Double,
             val authorRatingSum: Double,
             val email: String,
             val threshold: Double,
             val isAccountActive: Boolean,

             @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "author")
             val recipes: MutableSet<Recipe>) : AbstractJpaPersistable<Author>(), Serializable {

}
