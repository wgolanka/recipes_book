package com.recipebook.domain.recipe.dto

import com.fasterxml.jackson.annotation.JsonBackReference
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class MeasurementUnit(var unit: String) : AbstractJpaPersistable<MeasurementUnit>(), Serializable {

    @JsonBackReference
    @OneToOne(cascade = [CascadeType.ALL])
    var ingredient: Ingredient? = null
}
