/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.domain.user.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.aggregate.PasswordEncryptor
import com.flab.hsw.core.domain.user.exception.SameEmailUserAlreadyExistException
import com.flab.hsw.core.domain.user.exception.SameLoginIdUserAlreadyExistException
import com.flab.hsw.core.domain.user.exception.SameNicknameUserAlreadyExistException
import com.flab.hsw.core.domain.user.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt

/**
 * @since 2021-08-10
 */
interface CreateUserUseCase {
    fun createUser(message: CreateUserMessage): User

    interface CreateUserMessage {
        val nickname: String
        val email: String
        val loginId: String
        val password: String
    }

    companion object {
        fun newInstance(
            userRepository: UserRepository
        ): CreateUserUseCase = CreateUserUseCaseImpl(
            userRepository,
            passwordEncryptor = PasswordEncryptor.newInstance()
        )
    }
}

@UseCase
internal class CreateUserUseCaseImpl(
    private val users: UserRepository,
    private val passwordEncryptor: PasswordEncryptor
) : CreateUserUseCase {
    override fun createUser(message: CreateUserUseCase.CreateUserMessage): User {
        users.findByLoginId(message.loginId)?.let { throw SameLoginIdUserAlreadyExistException(message.loginId) }
        users.findByNickname(message.nickname)?.let { throw SameNicknameUserAlreadyExistException(message.nickname) }
        users.findByEmail(message.email)?.let { throw SameEmailUserAlreadyExistException(message.email) }

        val user = User.create(
            nickname = message.nickname,
            email = message.email,
            loginId = message.loginId,
            password = BCrypt.hashpw(message.password, BCrypt.gensalt())
        )

        return users.save(user)
    }
}
