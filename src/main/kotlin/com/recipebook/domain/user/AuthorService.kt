package com.recipebook.domain.user

import javassist.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class AuthorService(private val authorRepository: AuthorRepository) {

    fun create(nickname: String, nickNameColorId: Int, password: String, authorRating: Double, email: String,
               threshold: Int, isAccountActive: Boolean) {

        val newAuthor =
                Author(nickname,
                        nickNameColorId,
                        password,
                        authorRating,
                        0.0,
                        email,
                        threshold,
                        isAccountActive)

        authorRepository.saveAndFlush(newAuthor)
    }

    fun getCurrentUser(): Author {
        if (currentDefaultUser != null) {
            return currentDefaultUser as Author
        }
        val allUsers = authorRepository.findAll()
        if (allUsers.isEmpty()) {
            val newUser = Author("Arthur", 1, "pass", 10.2,
                    12.1, "smth@smth.com", 20, true)
            authorRepository.save(newUser)

            return authorRepository.findAll()[0]
        }

        return allUsers[0]
    }

    fun setCurrentUser(id: UUID) {
        currentDefaultUser = authorRepository.findByIdIs(id) //TODO handle null
    }

    fun getAll(): MutableList<Author> {
        return authorRepository.findAll()
    }

    fun getByEmail(email: String): Author {
        val author = authorRepository.findByEmailIs(email)

        if (author == null) {
            throw NotFoundException("No author with email: $email")
        } else {
            return author
        }
    }

    companion object {
        var currentDefaultUser: Author? = null
    }
}