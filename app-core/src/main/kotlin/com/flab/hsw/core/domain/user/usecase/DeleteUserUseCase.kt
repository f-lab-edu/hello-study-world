/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.domain.user.usecase

import com.flab.hsw.core.annotation.UseCase
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.aggregate.UserModel
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import java.util.*

/**
 * @since 2021-08-10
 */
interface DeleteUserUseCase {
    fun deleteUserById(id: UUID): User

    companion object {
        fun newInstance(
            userRepository: UserRepository
        ): DeleteUserUseCase = DeleteUserUseCaseImpl(
            userRepository
        )
    }
}

@UseCase
internal class DeleteUserUseCaseImpl(
    private val users: UserRepository
) : DeleteUserUseCase {
    override fun deleteUserById(id: UUID): User {
        val existingUser = users.findByUuid(id) ?: throw UserByIdNotFoundException(id)

        return users.save(UserModel.from(existingUser).delete())
    }
}
