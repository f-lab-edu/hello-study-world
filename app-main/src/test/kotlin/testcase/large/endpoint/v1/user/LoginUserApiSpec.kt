package testcase.large.endpoint.v1.user

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.endpoint.v1.user.common.UserResponse
import com.flab.hsw.endpoint.v1.user.create.CreateUserRequest
import com.flab.hsw.endpoint.v1.user.login.UserLoginRequest
import com.flab.hsw.endpoint.v1.user.login.UserLoginResponse
import com.github.javafaker.service.FakeValuesService
import com.github.javafaker.service.RandomService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import test.endpoint.v1.user.createRandomUser
import test.endpoint.v1.user.getUserApi
import test.endpoint.v1.user.loginUserApi
import test.endpoint.v1.user.random
import testcase.large.endpoint.EndpointLargeTestBase
import java.util.*

class LoginUserApiSpec : EndpointLargeTestBase() {

    @DisplayName("사용자가 입력한 아이디가 올바르지 않는 경우, 400 Bad Request를 반환합니다.")
    @Test
    fun idIsMalformed() {
        // given:
        val idBiggerThanLimit =
            FakeValuesService(Locale.ENGLISH, RandomService()).regexify("[a-z0-9]{${User.LENGTH_LOGIN_ID_MAX + 1},}")
        val randomPassword = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)

        // expect:
        loginUserApi(
            UserLoginRequest(
                loginId = idBiggerThanLimit,
                password = randomPassword
            )
        ).expect4xx()
    }

    @DisplayName("사용자가 입력한 패스워드가 올바르지 않는 경우, 400 Bad Request 를 반환합니다.")
    @Test
    fun passwordIsMalformed() {
        // given:
        val randomId = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX)
        val passwordBiggerThanLimit =
            FakeValuesService(Locale.ENGLISH, RandomService()).regexify("[a-z0-9]{${User.LENGTH_PASSWORD_MAX + 1}}")

        // expect:
        loginUserApi(
            UserLoginRequest(
                loginId = randomId,
                password = passwordBiggerThanLimit
            )
        ).expect4xx()
    }

    @DisplayName("사용자가 입력한 아이디가 존재하지 않는 경우, 404 Not Found 를 반환합니다.")
    @Test
    fun userIdIsNotFound() {
        // given:
        val normalId = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX)
        val normalPassword = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)

        // expect:
        loginUserApi(
            UserLoginRequest(
                loginId = normalId,
                password = normalPassword
            )
        ).expect4xx(HttpStatus.NOT_FOUND)
    }

    @DisplayName("사용자가 입력한 패스워드가 저장된 패스워드와 일치하지 않는 경우, 401 Unauthorized 를 반환합니다.")
    @Test
    fun passwordIsNotMatched() {
        // given:
        val setUpUser = createRandomUser()
        val randomPassword = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)

        // expect
        loginUserApi(
            UserLoginRequest(
                loginId = setUpUser.loginId,
                password = randomPassword
            )
        ).expect4xx(HttpStatus.UNAUTHORIZED)
    }

    @DisplayName("사용자가 로그인에 성공한 경우, 정상적으로 'lastActiveAt' 프로퍼티가 업데이트되며, 세션에 유저가 저장됩니다.")
    @Test
    fun lastActiveAtPropertyIsUpdatedWhenNormalCase() {
        // given:
        val preparedPassword = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)
        val preparedUser = createRandomUser(CreateUserRequest.random(password = preparedPassword))
        val preparedLastActiveTime = preparedUser.lastActiveAt

        // when:
        loginUserApi(
            UserLoginRequest(
                loginId = preparedUser.loginId,
                password = preparedPassword
            )
        ).expect2xx(UserLoginResponse::class)

        // and:
        val loginSuccessUser = getUserApi(preparedUser.id).expect2xx(UserResponse::class)

        // then:
        assertThat(loginSuccessUser.lastActiveAt > preparedLastActiveTime,`is`(true))
    }
}