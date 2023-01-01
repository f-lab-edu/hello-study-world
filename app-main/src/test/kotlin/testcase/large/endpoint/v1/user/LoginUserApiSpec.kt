package testcase.large.endpoint.v1.user

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.endpoint.ApiPaths
import com.flab.hsw.endpoint.v1.user.common.UserResponse
import com.flab.hsw.endpoint.v1.user.create.CreateUserRequest
import com.flab.hsw.endpoint.v1.user.login.UserLoginRequest
import com.flab.hsw.endpoint.v1.user.login.UserLoginResponse
import com.flab.hsw.util.JwtTokenManager
import com.github.javafaker.service.FakeValuesService
import com.github.javafaker.service.RandomService
import io.restassured.http.Header
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import test.endpoint.v1.user.*
import testcase.large.endpoint.EndpointLargeTestBase
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*

class LoginUserApiSpec : EndpointLargeTestBase() {

    @Value("\${auth.rsa.public}")
    private lateinit var publicKey: String

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

    @DisplayName("사용자가 로그인에 성공한 경우, 정상적으로 'lastActiveAt' 프로퍼티가 업데이트되며, 인증 토큰(Access, Refresh)이 발행됩니다.")
    @Test
    fun lastActiveAtPropertyIsUpdatedWhenNormalCase() {
        // given:
        val preparedPassword = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)
        val preparedUser = createRandomUser(CreateUserRequest.random(password = preparedPassword))
        val preparedLastActiveTime = preparedUser.lastActiveAt
        val publicKey = KeyFactory.getInstance("RSA")
            .generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)))

        // when:
        val response = loginUserApi(
            UserLoginRequest(
                loginId = preparedUser.loginId,
                password = preparedPassword
            )
        )

        // and:
        val loginSuccessUser = getUserApi(preparedUser.id).expect2xx(UserResponse::class)

        val issuedAccessToken = getAuthorizedTokenFrom(response.expect2xx(UserLoginResponse::class))
        val loginIdFromAccessToken = getSubjectFrom(issuedAccessToken, publicKey)

        val issuedRefreshToken = response.getCookie(JwtTokenManager.REFRESH_TOKEN_COOKIE_KEY)
        val loginIdFromRefreshToken = getSubjectFrom(issuedRefreshToken, publicKey)

        // then:
        assertAll(
            { assertThat(loginSuccessUser.lastActiveAt > preparedLastActiveTime, `is`(true)) },
            { assertThat(loginIdFromAccessToken, `is`(preparedUser.loginId)) },
            { assertThat(loginIdFromRefreshToken, `is`(preparedUser.loginId)) },
        )
    }
}
