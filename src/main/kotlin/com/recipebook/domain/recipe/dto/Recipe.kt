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

             var recipeImage: String?,

             var recipePrivate: Boolean,

             @ElementCollection(fetch = FetchType.EAGER)
             var ingredients: MutableList<Ingredient>,

             @ElementCollection
             var steps: List<String>,

             @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "recipe")
             var tagsIds: MutableSet<Tag>,

             @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "recipe")
             var comments: MutableSet<Comment>

) : AbstractJpaPersistable<Recipe>(), Serializable {

    @ManyToOne
    var author: Author? = null

    init {
       tagsIds.forEach { tag -> tag.setNewRecipe(this) }
    }
}