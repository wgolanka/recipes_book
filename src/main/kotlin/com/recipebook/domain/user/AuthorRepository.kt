package com.recipebook.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findByIdIs(id: UUID): Author
}