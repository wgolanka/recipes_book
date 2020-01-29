package com.recipebook.domain.user

import com.recipebook.domain.recipe.dto.Recipe
import com.recipebook.domain.recipe.RecipeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@RequestMapping("/user")
class AuthorController(val authorService: AuthorService, val authorRepository: AuthorRepository,
                       val recipeService: RecipeService) {

//    @PostMapping(value = ["/add"])
//    @ResponseStatus(HttpStatus.OK)
//    fun addNewUser(@RequestBody author: Author) {
//
//        if (!userExist(author.emailAddress)) {
//            val newUser = Author(author.nickname,
//                    author.emailAddress,
//                    author.nicknameColorId,
//                    author.threshold,
//                    author.avatarImg,
//                    author.createdCommentsIds,
//                    author.createdRecipesIds,
//                    author.createdRatingsIds,
//                    author.isAccountActive)
//
//            authorRepository.save(newUser)
//        }
//    }
//
//    fun userExist(emailAddress: String): Boolean {
//        return authorRepository.findAll().stream().noneMatch { brewer ->
//            brewer.emailAddress == emailAddress
//        }
//    }
//
//    @GetMapping(value = ["/all"])
//    fun getUsers(): ResponseEntity<MutableList<Author>> {
//        val all = authorRepository.findAll()
//        return ok(all)
//    }
//
//    @GetMapping("/recipes")
//    fun getUserRecipes(@RequestParam(required = true) id: String): ResponseEntity<List<Recipe>> { //TODO
//        val person = authorRepository.findByIdIs(UUID.fromString(id))
//        return ok(recipeService.getUserRecipes(person))
//    }
//
//    fun getAll(): MutableList<Author> {
//        return authorRepository.findAll()
//    }
//
//    @PutMapping(value = ["/setCurrent"])
//    @ResponseStatus(HttpStatus.OK)
//    fun setCurrentUser(@RequestParam(required = true) uuid: UUID) {
//        val exist = getAll().stream().anyMatch { user -> user.getId() == uuid }
//        if (exist) {
//            authorService.setCurrentUser(uuid)
//        }
//    }
}
