package com.recipebook.domain.recipe.dto

import com.recipebook.domain.user.Author
import com.recipebook.orm.AbstractJpaPersistable
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
class Recipe(var title: String,

             var description: String,

             var rating: Double,

             @Column(name = "notAuthorId")
             val authorId: UUID,

             val recipeImage: String?,

             val isRecipePrivate: Boolean,

             @ElementCollection
             var ingredients: List<Ingredient>,

             @ElementCollection
             val steps: List<String>,

             @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "recipe")
             var tagsIds: MutableSet<Tag>,

             @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "recipe")
             val comments: MutableSet<Comment>

) : AbstractJpaPersistable<Recipe>(), Serializable {

    @ManyToOne
    var author: Author? = null

    init {
       tagsIds.forEach { tag -> tag.setNewRecipe(this) }
    }


//    fun addIngredient(ingredient: Ingredient) {
//        if (!ingredients.contains(ingredient)) {
//            ingredients.plus(ingredient)
//            ingredient.addRecipe(this)
//        }
//    }
}