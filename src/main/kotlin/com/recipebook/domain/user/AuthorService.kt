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
                    "smth@smth.com", 20, true)
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

    fun getById(id: UUID): Author? {
        return authorRepository.findByIdIs(id) ?: return null
    }

    fun update(id: UUID?, nickname: String, nicknameColorId: Int, password: String, authorRating: Double,
               threshold: Int, accountActive: Boolean) {

        val author = authorRepository.findByIdIs(id!!) ?: throw NotFoundException("User with id $id doesn't exist")

        author.nickname = nickname
        author.nicknameColorId = nicknameColorId
        author.password = password
        author.threshold = threshold
        author.accountActive = accountActive

        authorRepository.saveAndFlush(author)
    }

    fun authorizeAndGet(authorEmail: String, authorPassword: String): Author? {
        val author = authorRepository.findByEmailIs(authorEmail) ?: return null

        return if (author.password == authorPassword) {
            author
        } else {
            null
        }
    }

    fun refreshRating(authorId: UUID) {
        val author = authorRepository.findByIdIs(authorId) ?: throw Exception()

        var sum = 0.0
        author.recipes.forEach { nextRecipe ->
            sum += nextRecipe.rating
        }

        if (sum > 0.0 && author.recipes.size > 0) {
            author.authorRating = sum / author.recipes.size
        }
    }

    fun addFavorite(authorId: UUID, recipeId: UUID): Author? {
        val author = authorRepository.findByIdIs(authorId) ?: return null
        author.favoriteRecipes.add(recipeId)
        return author
    }

    fun removeFavorite(authorId: UUID, recipeId: UUID): Author? {
        val author = authorRepository.findByIdIs(authorId) ?: return null
        author.favoriteRecipes.remove(recipeId)

        return author
    }

    companion object {
        var currentDefaultUser: Author? = null
    }
}