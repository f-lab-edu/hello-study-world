package com.flab.hsw.core.domain.user.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.aggregate.PasswordEncryptor
import com.flab.hsw.core.domain.user.exception.InvalidUserPasswordException
import com.flab.hsw.core.domain.user.exception.UserByLoginIDNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository

interface UserLoginUseCase {

    fun loginProcess(message: UserLoginMessage): User

    interface UserLoginMessage {
        val loginId: String
        val password: String
    }

    companion object {
        fun newInstance(
            userRepository: UserRepository,
            passwordEncryptor: PasswordEncryptor = PasswordEncryptor.newInstance()
        ): UserLoginUseCase = UserLoginUseCaseImpl(
            userRepository,
            passwordEncryptor
        )
    }

}

@UseCase
internal class UserLoginUseCaseImpl(
    private val userRepository: UserRepository,
    private val passwordEncryptor: PasswordEncryptor
) : UserLoginUseCase {
    override fun loginProcess(message: UserLoginUseCase.UserLoginMessage): User {
        val findUser = userRepository.findByLoginId(message.loginId)
            ?: throw UserByLoginIDNotFoundException(message.loginId)

        return findUser.also {
            if(!passwordEncryptor.isMatched(message.password, findUser.password)){
                throw InvalidUserPasswordException()
            }
            findUser.updateLastActiveTimeToNow()
            userRepository.update(findUser)
        }

    }
}
