package com.recipebook.domain.recipe.dto

import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class RecipeRating : AbstractJpaPersistable<RecipeRating>(), Serializable {

    @OneToOne
    var recipe: Recipe? = null

    var ratingSum = 0.0

    var numberOfRatings = 0

    fun getRating(): Double {
        return ratingSum / numberOfRatings
    }

    fun addNew(rating: Double) {
        ratingSum += rating
        numberOfRatings += 1
    }
}
