/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.medium.endpoint.v1.user

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.endpoint.v1.ApiPathsV1
import com.github.javafaker.Faker
import com.github.javafaker.service.FakeValuesService
import com.github.javafaker.service.RandomService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import testcase.medium.ControllerMediumTestBase
import java.util.*
import java.util.stream.Stream

/**
 * @since 2021-08-10
 */
class CreateUserRequestSpec : ControllerMediumTestBase() {
    @ParameterizedTest(name = "Fails if it is {0} characters")
    @MethodSource("badNicknames")
    fun failIfNicknamesAreBad(
        testName: String,
        nickname: String
    ) {
        // given:
        val payload = FakeCreateUserRequest(
            nickname = nickname,
            email = Faker().internet().emailAddress(),
            loginId = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX),
            password = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)
        )

        // when:
        val request = post(ApiPathsV1.USERS, payload)

        // then:
        val errorResponse = request.send().expect4xx()

        // expect:
        assertThat(ErrorCodes.from(errorResponse.code), `is`(ErrorCodes.WRONG_INPUT))
    }

    @ParameterizedTest(name = "Fails if it is {0}")
    @MethodSource("badEmails")
    fun failIfEmailsAreBad(
        testName: String,
        email: String
    ) {
        // given:
        val payload = FakeCreateUserRequest(
            nickname = Faker().name().fullName(),
            email = email,
            loginId = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX),
            password = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)
        )

        // when:
        val request = post(ApiPathsV1.USERS, payload)

        // then:
        val errorResponse = request.send().expect4xx()

        // expect:
        assertThat(ErrorCodes.from(errorResponse.code), `is`(ErrorCodes.WRONG_INPUT))
    }

    @ParameterizedTest(name = "Fails if it is {0}")
    @MethodSource("badLoginIds")
    fun failIfLoginIdsAreBad(
        testName: String,
        loginId: String
    ) {
        // given:
        val payload = FakeCreateUserRequest(
            nickname = Faker().name().fullName(),
            email = Faker().internet().emailAddress(),
            loginId = loginId,
            password = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX)
        )

        // when:
        val request = post(ApiPathsV1.USERS, payload)

        // then:
        val errorResponse = request.send().expect4xx()

        // expect:
        assertThat(ErrorCodes.from(errorResponse.code), `is`(ErrorCodes.WRONG_INPUT))
    }

    @ParameterizedTest(name = "Fails if it is {0}")
    @MethodSource("badPasswords")
    fun failIfPasswordsAreBad(
        testName: String,
        password: String
    ) {
        // given:
        val payload = FakeCreateUserRequest(
            nickname = Faker().name().fullName(),
            email = Faker().internet().emailAddress(),
            loginId = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX),
            password = password
        )

        // when:
        val request = post(ApiPathsV1.USERS, payload)

        // then:
        val errorResponse = request.send().expect4xx()

        // expect:
        assertThat(ErrorCodes.from(errorResponse.code), `is`(ErrorCodes.WRONG_INPUT))
    }

    @JsonDeserialize
    private data class FakeCreateUserRequest(
        val nickname: String?,
        val email: String?,
        val loginId: String,
        val password: String
    )

    companion object {
        @JvmStatic
        fun badNicknames(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "shorter than ${User.LENGTH_NICKNAME_MIN}",
                Faker().letterify("?").repeat(User.LENGTH_NICKNAME_MIN - 1),
            ),
            Arguments.of(
                "longer than ${User.LENGTH_NICKNAME_MAX}",
                Faker().letterify("?").repeat(User.LENGTH_NICKNAME_MAX + 1),
            )
        )

        @JvmStatic
        fun badEmails(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "empty",
                "",
            ),
            Arguments.of(
                "not an IETF email format",
                Faker().lorem().word(),
            ),
            Arguments.of(
                "longer than ${User.LENGTH_EMAIL_MAX}",
                Faker().letterify("?").repeat(User.LENGTH_EMAIL_MAX + 1) + "@company.com",
            )
        )

        @JvmStatic
        fun badLoginIds(): Stream<Arguments> = Stream.of(
            Arguments.of("emptyValue", ""),
            Arguments.of(
                "Do not matched in given expression",
                FakeValuesService(Locale.ENGLISH, RandomService()).regexify("[a-z0-9]{${User.LENGTH_NICKNAME_MAX+1},}"),
                FakeValuesService(Locale.ENGLISH, RandomService()).regexify("[a-z0-9]{1,${User.LENGTH_LOGIN_ID_MIN}}")
            )
        )

        @JvmStatic
        fun badPasswords(): Stream<Arguments> = Stream.of(
            Arguments.of("emptyValue", ""),
            Arguments.of(
                "Do not matched in given expression",
                FakeValuesService(Locale.ENGLISH, RandomService()).regexify("[a-z0-9]{1,${User.LENGTH_PASSWORD_MIN}}"),
                FakeValuesService(Locale.ENGLISH, RandomService()).regexify("[a-z0-9]{${User.LENGTH_PASSWORD_MAX+1}}")
            )
        )
    }
}
