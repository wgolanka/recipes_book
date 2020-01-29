package com.recipebook.domain.recipe.dto

import java.util.*

class SkimRecipe(val id: UUID?,
                 val title: String,
                 val authorNickname: String?,
                 val nicknameColorId: Int?,
                 val rating: Double,
                 val recipeImage: String?)