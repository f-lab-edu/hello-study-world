/*
 * kopringboot-multimodule-template
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package com.flab.hsw.core.domain.user.aggregate

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.usecase.EditUserUseCase
import java.time.Instant
import java.util.*

/**
 * Implementation note is taken from: https://martinfowler.com/bliki/EvansClassification.html
 *
 * @since 2022-10-03
 */
internal data class UserModel(
    override val id: UUID,
    override val nickname: String,
    override val email: String,
    override val password: String,
    override val loginId: String,
    override val createdAt: Instant,
    override var lastActiveAt: Instant,
    override val deleted: Boolean
) : User {
    fun applyValues(values: EditUserUseCase.EditUserMessage): User = this.copy(
        nickname = values.nickname ?: this.nickname,
        email = values.email ?: this.email,
        lastActiveAt = Instant.now()
    )

    override fun delete(): User = this.copy(
        deleted = true,
        lastActiveAt = Instant.now()
    )

    override fun updateLastActiveTimeToNow() = changeTimeToNow()
    
    override fun changeTimeToNow(time: Instant) {
        lastActiveAt = time
    }

    companion object {
        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various User creation cases
        fun create(
            id: UUID = UUID.randomUUID(),
            nickname: String,
            email: String,
            loginId: String,
            password: String,
            createdAt: Instant? = null,
            lastActiveAt: Instant? = null,
            deleted: Boolean = false
        ): UserModel {
            val now = Instant.now()

            return UserModel(
                id = id,
                nickname = nickname,
                email = email,
                loginId = loginId,
                password = password,
                createdAt = createdAt ?: now,
                lastActiveAt = lastActiveAt ?: now,
                deleted = deleted
            )
        }

        fun from(user: User): UserModel = if (user is UserModel) {
            user
        } else {
            with(user) {
                create(
                    id = id,
                    nickname = nickname,
                    email = email,
                    loginId = loginId,
                    password = password,
                    createdAt = createdAt,
                    lastActiveAt = lastActiveAt,
                    deleted = deleted
                )
            }
        }
    }
}
