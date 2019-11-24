package com.recipebook.domain.user

import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getCurrentUser(): BaseUser {
//        val jack = UUID.fromString("")
        val sadie = UUID.fromString("6006c8f7-3f55-4e39-a644-c3f55d96f1e9") //default set user
        if (currentBaseUser == null) {
            setCurrentUser(sadie)
            return userRepository.findByIdIs(sadie)
        }
        return currentBaseUser as BaseUser
    }

    fun setCurrentUser(id: UUID) {
        currentBaseUser = userRepository.findByIdIs(id) //TODO handle null
    }

    companion object {
        var currentBaseUser: BaseUser? = null
    }
}