/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.domain.user

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.usecase.CreateUserUseCase
import com.flab.hsw.core.domain.user.usecase.EditUserUseCase
import com.github.javafaker.Faker
import com.github.javafaker.service.FakeValuesService
import com.github.javafaker.service.RandomService
import java.util.*

fun randomCreateUserMessage(
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    loginId: String = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX),
    password: String = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX),
): CreateUserUseCase.CreateUserMessage {
    data class FakeCreateUserMessage(
        override val nickname: String,
        override val email: String,
        override val loginId: String,
        override val password: String
    ) : CreateUserUseCase.CreateUserMessage

    return FakeCreateUserMessage(
        nickname = nickname,
        email = email,
        loginId = loginId,
        password = password
    )
}

fun randomEditUserMessage(
    nickname: String? = Faker().name().fullName(),
    email: String? = Faker().internet().emailAddress()
): EditUserUseCase.EditUserMessage {
    data class FakeEditUserMessage(
        override val nickname: String?,
        override val email: String?
    ) : EditUserUseCase.EditUserMessage

    return FakeEditUserMessage(
        nickname = nickname,
        email = email
    )
}
