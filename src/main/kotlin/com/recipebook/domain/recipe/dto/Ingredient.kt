package com.recipebook.domain.recipe.dto

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class Ingredient(val name: String,

                 val quantity: Double,

                 @OneToOne(cascade = [CascadeType.ALL])
                 val measurementUnit: MeasurementUnit) : AbstractJpaPersistable<Recipe>(), Serializable {

    init {
        measurementUnit.ingredient = this
    }
}

