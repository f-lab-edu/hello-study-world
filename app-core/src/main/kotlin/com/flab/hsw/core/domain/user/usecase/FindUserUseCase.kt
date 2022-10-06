/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.domain.user.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import java.util.*

/**
 * @since 2021-08-10
 */
interface FindUserUseCase {
    fun getUserById(id: UUID): User = findUserById(id) ?: throw UserByIdNotFoundException(id)

    fun findUserById(id: UUID): User?

    companion object {
        fun newInstance(
            userRepository: UserRepository
        ): FindUserUseCase = FindUserUseCaseImpl(
            userRepository
        )
    }
}

@UseCase
internal class FindUserUseCaseImpl(
    private val users: UserRepository
) : FindUserUseCase {
    override fun findUserById(id: UUID): User? {
        return users.findByUuid(id)
    }
}
