package com.recipebook.domain.user

import javassist.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class AuthorService(private val authorRepository: AuthorRepository) {

    fun create(author: Author) {

        val newAuthor =
                Author(author.nickname,
                        author.nicknameColorId,
                        author.password,
                        author.authorRating,
                        0.0,
                        author.email,
                        author.threshold,
                        author.accountActive)

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

    fun userExistByEmail(email: String): Boolean {
        return authorRepository.findByEmailIs(email) != null
    }

    fun userExistById(id: UUID): Boolean {
        return authorRepository.findByIdIs(id) != null
    }

    fun getByEmail(email: String): Author {
        val author = authorRepository.findByEmailIs(email)

        if (author == null) {
            throw NotFoundException("No author with email: $email")
        } else {
            return author
        }
    }

    fun getById(id: UUID): Author {
        val author = authorRepository.findByIdIs(id)

        if (author == null) {
            throw NotFoundException("No author with email: $id")
        } else {
            return author
        }
    }

    fun update(id: UUID?, nickname: String, nicknameColorId: Int, password: String, authorRating: Double,
               authorRatingSum: Double, email: String, threshold: Int, accountActive: Boolean) {

        val author = authorRepository.findByIdIs(id!!) ?: throw NotFoundException("User with id $id doesn't exist")

        author.email = email
        author.nickname = nickname
        author.nicknameColorId = nicknameColorId
        author.password = password
        author.authorRating = authorRating
        author.authorRatingSum = authorRatingSum
        author.threshold = threshold
        author.accountActive = accountActive

        authorRepository.saveAndFlush(author)
    }

    companion object {
        var currentDefaultUser: Author? = null
    }
}