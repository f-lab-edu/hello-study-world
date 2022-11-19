package testcase.small.domain.user

import com.flab.hsw.core.domain.user.aggregate.PasswordEncryptor
import com.flab.hsw.core.domain.user.exception.InvalidUserPasswordException
import com.flab.hsw.core.domain.user.exception.UserByLoginIDNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.core.domain.user.usecase.UserLoginUseCase
import com.flab.hsw.lib.annotation.SmallTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import test.domain.user.aggregate.randomUser
import test.domain.user.randomUserLoginMessage

@SmallTest
class UserLoginUseCaseSpec {
    private lateinit var sut: UserLoginUseCase
    private lateinit var userRepository: UserRepository
    private lateinit var passwordEncryptor: PasswordEncryptor

    @BeforeEach
    fun setup() {
        userRepository = mock()
        passwordEncryptor = mock()
        sut = UserLoginUseCase.newInstance(userRepository, passwordEncryptor)
    }

    @DisplayName("사용자가 입력한 아이디를 찾을 수 없는 경우, 예외가 발생합니다.")
    @Test
    fun failIfLoginIdIsNotFound() {
        // given:
        val message = randomUserLoginMessage()

        // and:
        `when`(userRepository.findByLoginId(any())).thenReturn(null)

        // when:
        assertThrows<UserByLoginIDNotFoundException> { sut.loginProcess(message) }
    }

    @DisplayName("사용자가 입력한 비밀번호가 일치하지 않는 경우, 예외가 발생합니다.")
    @Test
    fun failIfPasswordIsInvalid() {
        // given:
        val message = randomUserLoginMessage()

        // and:
        `when`(userRepository.findByLoginId(any())).thenReturn(randomUser())
        `when`(passwordEncryptor.isMatched(any(), any())).thenReturn(false)

        // when:
        assertThrows<InvalidUserPasswordException> { sut.loginProcess(message) }
    }

    @DisplayName("사용자가 입력한 비밀번호와 패스워드가 일치하는 경우, 회원의 최근 이용시간이 갱신됩니다.")
    @Test
    fun userLastActiveTimeIsUpdateToAfterCreateWhenLoginIsSuccess() {
        // given:
        val message = randomUserLoginMessage()
        val user = randomUser()

        // and:
        val timeBeforeLogin = user.lastActiveAt
        `when`(userRepository.findByLoginId(any())).thenReturn(user)
        `when`(passwordEncryptor.isMatched(any(), any())).thenReturn(true)

        // when:
        val loginUser = sut.loginProcess(message)

        // then:
        assertAll(
            { assertThat(loginUser.lastActiveAt.isAfter(timeBeforeLogin), `is`(true)) },
            { verify(userRepository, times(1)).update(user) }
        )
    }
}
