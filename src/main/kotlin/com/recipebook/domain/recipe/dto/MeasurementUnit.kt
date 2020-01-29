package com.recipebook.domain.recipe.dto

import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class MeasurementUnit(val unit: String) {
    @OneToOne
    var ingredient: Ingredient? = null
}
