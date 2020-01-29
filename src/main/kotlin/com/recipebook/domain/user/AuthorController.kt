package com.recipebook.domain.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
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
        return ok(authorService.getById(authorId))
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody author: Author): ResponseEntity<Author>? {
        if (!authorService.userExistById(author.getId()!!)) {
            return badRequest().body(null).statusCode(HttpStatus.NOT_FOUND) //TODO it works?
        }

        authorService.update(author.getId(),
                author.nickname,
                author.nicknameColorId,
                author.password,
                author.authorRating,
                author.authorRatingSum,
                author.email,
                author.threshold,
                author.accountActive)

        return ok(authorService.getById(author.getId()!!))
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

private operator fun HttpStatus.invoke(notFound: HttpStatus): ResponseEntity<Author>? {
    return null
}
