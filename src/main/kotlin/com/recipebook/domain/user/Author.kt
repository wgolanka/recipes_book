package com.recipebook.domain.user

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class Author(val nickname: String,
             val emailAddress: String,
             val nicknameColorId: Int,
             val threshold: String,
             val avatarImg: String,

             @ElementCollection
             val createdCommentsIds: MutableSet<UUID>,

             @ElementCollection
             val createdRecipesIds: MutableSet<UUID>,

             @ElementCollection
             val createdRatingsIds: MutableSet<UUID>,

             val isAccountActive: Boolean) : AbstractJpaPersistable<Author>(), Serializable {

    fun addCreatedRecipeId(recipeId: UUID) {
        if (!createdRecipesIds.contains(recipeId)) {
            createdRecipesIds.add(recipeId)
        }
    }

    fun removeRecipeId(recipeId: UUID) {
        createdRecipesIds.remove(recipeId)
    }
}
