package com.recipebook.domain.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class AuthorService(private val authorRepository: AuthorRepository) {
//
//    fun getCurrentUser(): Author {
//        if (currentDefaultUser != null){
//            return currentDefaultUser as Author
//        }
//
//        val allUsers = authorRepository.findAll()
//        if (allUsers.isEmpty()) {
//            val newUser = Author("Arthur", "athurt@arthur.com", 1, "?",
//                    "", mutableSetOf(), mutableSetOf(), mutableSetOf(), true)
//            authorRepository.save(newUser)
//
//            return authorRepository.findByIdIs(newUser.getId()!!)
//        }
//
//        return allUsers[0];
//    }
//
//    fun setCurrentUser(id: UUID) {
//        currentDefaultUser = authorRepository.findByIdIs(id) //TODO handle null
//    }
//
//    companion object {
//        var currentDefaultUser: Author? = null
//    }
}