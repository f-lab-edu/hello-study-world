/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.endpoint.v1.user

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.endpoint.v1.user.create.CreateUserRequest
import com.flab.hsw.endpoint.v1.user.edit.EditUserRequest
import com.github.javafaker.Faker
import com.github.javafaker.service.FakeValuesService
import com.github.javafaker.service.RandomService
import java.util.*

fun CreateUserRequest.Companion.random(
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    loginId: String = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX),
    password: String = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX),
) = CreateUserRequest(
    nickname = nickname,
    email = email,
    loginId = loginId,
    password = password
)

fun EditUserRequest.Companion.random(
    nickname: String? = Faker().name().fullName(),
    email: String? = Faker().internet().emailAddress()
) = EditUserRequest(
    nickname = nickname,
    email = email
)
