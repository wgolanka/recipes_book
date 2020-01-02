package com.recipebook.domain.user

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import java.util.*
import javax.persistence.Entity

@Entity
class Author(val nickname: String,
             val nicknameColorId: Int,
             val threshold: String,
             val avatarImg: String,
             val createdCommentsIds: MutableList<UUID>,
             val createdRecipesIds: MutableList<UUID>,
             val createdRatingsIds: MutableList<UUID>,
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
