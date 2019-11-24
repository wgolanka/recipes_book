package com.recipebook.domain.user

import com.recipebook.domain.recipe.Recipe
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@RequestMapping("/user")
class UserController(val userService: UserService, val userRepository: UserRepository) {

    @PostMapping(value = ["/add"])
    @ResponseStatus(HttpStatus.OK)
    fun addNewUser(@RequestParam(required = true) nickname: String,
                   @RequestParam(required = true) avatar: ByteArray,
                   @RequestParam(required = true) description: String,
                   @RequestParam(required = true) emailAddress: String) {

        if (userRepository.findAll().stream().noneMatch { brewer -> brewer.emailAddress == emailAddress }) {
            val newUser = BaseUser(nickname, avatar, LocalDate.now(), description, emailAddress)
            userRepository.save(newUser)
        }
    }

    @GetMapping(value = ["/all"])
    fun getUsers(): ResponseEntity<MutableList<BaseUser>> {
        val all = userRepository.findAll()
        return ok(all)
    }

    @GetMapping("/recipes")
    fun getUserRecipes(@RequestParam(required = true) id: String): ResponseEntity<MutableSet<Recipe>> { //TODO
        val person = userRepository.findByIdIs(UUID.fromString(id))
        return ok(person.createdRecipes)
    }

    fun getAll(): MutableList<BaseUser> {
        return userRepository.findAll()
    }

    @PutMapping(value = ["/setCurrent"])
    @ResponseStatus(HttpStatus.OK)
    fun setCurrentUser(@RequestParam(required = true) uuid: UUID) {
        val exist = getAll().stream().anyMatch { user -> user.getId() == uuid }
        if (exist) {
            userService.setCurrentUser(uuid)
        }
    }
}