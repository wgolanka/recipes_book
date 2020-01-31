package com.recipebook.domain.recipe.tag

import com.recipebook.domain.recipe.dto.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TagRepository : JpaRepository<Tag, Long> {
    fun getAllByIdIsNotNull(): List<Tag>
    fun findByIdIs(id: UUID): Tag?
    fun getTagByIdEquals(id: UUID?): Tag?
    fun deleteByIdEquals(id: UUID?)
}