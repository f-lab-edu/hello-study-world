/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.domain.user

import com.flab.hsw.core.domain.SoftDeletable
import com.flab.hsw.core.domain.user.aggregate.UserModel
import java.time.Instant
import java.util.*

/**
 * @since 2021-08-10
 */
interface User : SoftDeletable {
    val id: UUID

    val nickname: String

    val email: String

    val loginId: String

    val password: String

    val registeredAt: Instant

    val lastActiveAt: Instant

    companion object {
        const val LENGTH_NICKNAME_MIN = 2
        const val LENGTH_NICKNAME_MAX = 64
        const val LENGTH_LOGIN_ID_MIN = 5
        const val LENGTH_LOGIN_ID_MAX = 20
        const val LENGTH_PASSWORD_MIN = 8
        const val LENGTH_PASSWORD_MAX = 16
        const val LENGTH_EMAIL_MAX = 64

        const val LOGIN_ID_REGEX = "[a-z0-9]{${LENGTH_LOGIN_ID_MIN},${LENGTH_LOGIN_ID_MAX}}"
        const val PASSWORD_REGEX = "[a-z0-9]{${LENGTH_PASSWORD_MIN},${LENGTH_PASSWORD_MAX}}"

        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various User creation cases
        fun create(
            id: UUID = UUID.randomUUID(),
            nickname: String,
            email: String,
            loginId: String,
            password: String,
            registeredAt: Instant? = null,
            lastActiveAt: Instant? = null,
            deleted: Boolean = false
        ): User = UserModel.create(
            id = id,
            nickname = nickname,
            email = email,
            loginId = loginId,
            password = password,
            registeredAt = registeredAt,
            lastActiveAt = lastActiveAt,
            deleted = deleted
        )
    }
}
