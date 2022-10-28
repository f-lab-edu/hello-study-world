/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.small.domain.user

import com.flab.hsw.core.domain.user.exception.SameEmailUserAlreadyExistException
import com.flab.hsw.core.domain.user.exception.SameLoginIdUserAlreadyExistException
import com.flab.hsw.core.domain.user.exception.SameNicknameUserAlreadyExistException
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.core.domain.user.usecase.CreateUserUseCase
import com.flab.hsw.lib.annotation.SmallTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import test.domain.user.aggregate.randomUser
import test.domain.user.aggregate.equalsWhetherPasswordIsEncryptedOrNot
import test.domain.user.randomCreateUserMessage

/**
 * @since 2021-08-10
 */
@SmallTest
class CreateUserUseCaseSpec {
    private lateinit var sut: CreateUserUseCase
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        userRepository = mock()
        sut = CreateUserUseCase.newInstance(userRepository)

        `when`(userRepository.save(any())).thenAnswer { return@thenAnswer it.arguments[0] }
    }

    @DisplayName("An user object that fully represents message, is created")
    @Test
    fun userIsCreated() {
        // given:
        val message = randomCreateUserMessage()

        // when:
        val createdUser = sut.createUser(message)

        // then:
        assertAll(
            { assertThat(createdUser.nickname, `is`(message.nickname)) },
            { assertThat(createdUser.email, `is`(message.email)) },
            { assertThat(createdUser.loginId, `is`(message.loginId)) },
            { assertThat(equalsWhetherPasswordIsEncryptedOrNot(message.password, createdUser.password), `is`(true))}
        )
    }

    @DisplayName("Nickname must not be duplicated")
    @Test
    fun errorIfNicknameIsDuplicated() {
        // given:
        val message = randomCreateUserMessage()

        // and:
        `when`(userRepository.findByNickname(message.nickname)).thenReturn(randomUser(nickname = message.nickname))

        // then:
        assertThrows<SameNicknameUserAlreadyExistException> { sut.createUser(message) }
    }

    @DisplayName("Email must not be duplicated")
    @Test
    fun errorIfEmailIsDuplicated() {
        // given:
        val message = randomCreateUserMessage()

        // and:
        `when`(userRepository.findByEmail(message.email)).thenReturn(randomUser(email = message.email))

        // then:
        assertThrows<SameEmailUserAlreadyExistException> { sut.createUser(message) }
    }

    @DisplayName("Login Id must not be duplicated")
    @Test
    fun errorIfLoginIdIsDuplicated() {
        // given:
        val message = randomCreateUserMessage()

        // and:
        `when`(userRepository.findByLoginId(message.loginId)).thenReturn(randomUser(loginId = message.loginId))

        // then:
        assertThrows<SameLoginIdUserAlreadyExistException> { sut.createUser(message) }
    }

}
