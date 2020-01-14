package com.recipebook

import com.recipebook.domain.recipe.RecipeRepository
import com.recipebook.domain.user.AuthorRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class RecipeRepoTest(@Autowired val repository: RecipeRepository,
                     @Autowired val authorRepository: AuthorRepository) {
    //todo make test connect to test db
    @Test
    fun `Get all brews by author id`() {
//        val person = BaseUser("SadieAdler", ByteArray(1), LocalDate.now(), "I'm Sadie!",
//                "sadie.adler@gmail.com")

//        val brew = Tea("Green", "",
//                "", person, "", 0.0)
//
//        val hashCodeBefore = brew.hashCode()
//
//        repository.save(brew)
//        userRepository.save(person)
//
//        val hashCodeAfter = brew.hashCode()
//        assertThat(person.createdBrews).contains(brew)
//        assertThat(repository.findAll()).contains(brew)
//        assertThat(repository.getAllByAuthorIs(person)).contains(brew)
//        assertThat(hashCodeAfter).isEqualTo(hashCodeBefore)
    }
}