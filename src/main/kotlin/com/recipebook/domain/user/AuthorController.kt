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
class AuthorController(val authorService: AuthorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody author: Author): ResponseEntity<Author> {
        if (!authorService.userExistByEmail(author.email)) {
            authorService.create(author)
        }

        return ok(authorService.getByEmail(author.email))
    }

    @GetMapping
    fun getUsers(): ResponseEntity<MutableList<Author>> {
        return ok(authorService.getAll())
    }

    @GetMapping("/{authorId}")
    fun getUser(@PathVariable("authorId") authorId: UUID): ResponseEntity<Author> {
        val author = authorService.getById(authorId)
                ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)

        return ok(author)
    }

    @GetMapping("/{authorEmail}/{authorPassword}")
    fun getUserWithAuthorization(@PathVariable("authorEmail") authorEmail: String,
                                 @PathVariable("authorPassword") authorPassword: String): ResponseEntity<Author> {

        val author = authorService.authorizeAndGet(authorEmail, authorPassword)
                ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)

        return ok(author)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody author: Author): ResponseEntity<Author>? {
        if (!authorService.userExistById(author.getId()!!)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        authorService.update(author.getId(),
                author.nickname,
                author.nicknameColorId,
                author.password,
                author.authorRating,
                author.threshold,
                author.accountActive)

        return ok(authorService.getById(author.getId()!!)!!)
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