/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.domain.user

import com.flab.hsw.core.domain.user.usecase.CreateUserUseCase
import com.flab.hsw.core.domain.user.usecase.EditUserUseCase
import com.github.javafaker.Faker

fun randomCreateUserMessage(
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    loginId: String = "",
    password: String = ""
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
