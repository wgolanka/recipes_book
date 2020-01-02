package com.recipebook.domain.recipe

import com.fasterxml.jackson.annotation.JsonIgnore
import com.recipebook.domain.user.Author
import com.recipebook.orm.AbstractJpaPersistable
import com.sun.xml.fastinfoset.util.StringArray
import java.io.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Recipe(var title: String,
             var description: String,
             val authorId: UUID,
             val ratingsIds: List<String>,
             val measurementSystemId: Int,
             val tagsIds: List<String>,
             val recipeImage: String,
             val isRecipePrivate: Boolean, //TODO We have thing like that?
             val steps: List<String>,
             val ingredients: List<Ingredient>,
             val commentsIds: List<UUID>) : AbstractJpaPersistable<Recipe>(), Serializable {

}