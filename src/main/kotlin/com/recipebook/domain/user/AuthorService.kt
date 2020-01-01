package com.recipebook.domain.user

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    fun getCurrentUser(): Author {
        if (currentDefaultUser != null){
            return currentDefaultUser as Author
        }

        val allUsers = authorRepository.findAll()
        if (allUsers.isEmpty()) {
            val newUser = Author("Arthur", byteArrayOf(), LocalDate.now(), "This is arthur",
                    "Arthur@rdr2.com")
            authorRepository.save(newUser)
            return authorRepository.findByIdIs(newUser.getId()!!)
        }

        return allUsers[0];
    }

    fun setCurrentUser(id: UUID) {
        currentDefaultUser = authorRepository.findByIdIs(id) //TODO handle null
    }

    companion object {
        var currentDefaultUser: Author? = null
    }
}