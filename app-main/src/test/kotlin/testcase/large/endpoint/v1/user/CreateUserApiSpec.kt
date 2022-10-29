/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.large.endpoint.v1.user

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.endpoint.v1.user.common.UserResponse
import com.flab.hsw.endpoint.v1.user.create.CreateUserRequest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import test.endpoint.v1.user.createRandomUser
import test.endpoint.v1.user.createUserApi
import test.endpoint.v1.user.random
import testcase.large.endpoint.EndpointLargeTestBase

/**
 * @since 2021-08-10
 */
class CreateUserApiSpec : EndpointLargeTestBase() {
    @DisplayName("User is created for valid request")
    @Test
    fun userCreated() {
        // given:
        val request = CreateUserRequest.random()

        // then:
        val response = createUserApi(
            request,
            requestFields = listOf(
                fieldWithPath("nickname").type(STRING).description("별명"),
                fieldWithPath("email").type(STRING).description("이메일"),
                fieldWithPath("loginId").type(STRING).description("로그인 ID"),
                fieldWithPath("password").type(STRING).description("비밀번호"),
            ),
            responseFields = listOf(
                fieldWithPath("id").type(STRING).description("UUID"),
                fieldWithPath("nickname").type(STRING).description("별명"),
                fieldWithPath("email").type(STRING).description("이메일"),
                fieldWithPath("loginId").type(STRING).description("로그인 ID"),
                fieldWithPath("registeredAt").type(STRING).description("등록 일시"),
                fieldWithPath("lastActiveAt").type(STRING).description("마지막 활성 일시")
            )
        ).expect2xx(UserResponse::class)

        // expect:
        assertThat(response, isReflecting = request)
    }

    @DisplayName("Cannot create user if:")
    @Nested
    inner class CannotCreateUserIf {
        private lateinit var createdUser: UserResponse

        @BeforeEach
        fun setUp() {
            createdUser = createRandomUser()
        }

        @DisplayName("Nickname is duplicated")
        @Test
        fun nicknameIsDuplicated() {
            // expect:
            createUserApi(CreateUserRequest.random(nickname = createdUser.nickname))
                .expect4xx(HttpStatus.CONFLICT)
                .withExceptionCode(ErrorCodes.USER_BY_NICKNAME_DUPLICATED)
        }

        @DisplayName("Email is duplicated")
        @Test
        fun emailIsDuplicated() {
            // expect:
            createUserApi(CreateUserRequest.random(email = createdUser.email))
                .expect4xx(HttpStatus.CONFLICT)
                .withExceptionCode(ErrorCodes.USER_BY_EMAIL_DUPLICATED)
        }

        @DisplayName("Login Id is duplicated")
        @Test
        fun loginIdIsDuplicated() {
            // expect:
            createUserApi(CreateUserRequest.random(loginId = createdUser.loginId))
                .expect4xx(HttpStatus.CONFLICT)
                .withExceptionCode(ErrorCodes.USER_BY_LOGIN_ID_DUPLICATED)
        }
    }

    private fun assertThat(actual: UserResponse, isReflecting: CreateUserRequest) {
        assertAll(
            { assertThat(actual.nickname, `is`(isReflecting.nickname)) },
            { assertThat(actual.email, `is`(isReflecting.email)) },
            { assertThat(actual.loginId, `is`(isReflecting.loginId)) }
        )
    }
}
