package com.recipebook.domain.recipe.dto

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class MeasurementUnit(val unit: String) : AbstractJpaPersistable<MeasurementUnit>(), Serializable {
    @OneToOne
    var ingredient: Ingredient? = null
}
