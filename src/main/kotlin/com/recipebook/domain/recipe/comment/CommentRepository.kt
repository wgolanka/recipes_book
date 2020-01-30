package com.recipebook.domain.recipe.comment

import com.recipebook.domain.recipe.dto.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun getAllByIdIsNotNull(): List<Comment>
    fun findByIdIs(id: UUID): Comment?
    fun getCommentByIdEquals(id: UUID?): Comment?
    fun deleteByIdEquals(id: UUID?)
}