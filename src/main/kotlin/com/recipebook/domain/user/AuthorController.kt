package com.recipebook.domain.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@RequestMapping("/authors")
class AuthorController(val authorService: AuthorService, val authorRepository: AuthorRepository) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody author: Author): ResponseEntity<Author> {
        if (!userExist(author.email)) {
            authorService.create(author.nickname,
                    author.nicknameColorId,
                    author.password,
                    author.authorRating,
                    author.email,
                    author.threshold,
                    author.isAccountActive)
        }

        return ok(authorService.getByEmail(author.email))
    }

    fun userExist(email: String): Boolean {
        return authorRepository.findByEmailIs(email) != null
    }

    @GetMapping()
    fun getUsers(): ResponseEntity<MutableList<Author>> {
        return ok(authorService.getAll())
    }

    @PutMapping(value = ["/current"])
    @ResponseStatus(HttpStatus.OK)
    fun setCurrentUser(@RequestParam(required = true) id: UUID) {
        val exist = authorService.getAll().stream().anyMatch { user -> user.getId() == id }
        if (exist) {
            authorService.setCurrentUser(id)
        }
    }
}
