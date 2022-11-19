/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.domain.user.repository

import com.flab.hsw.core.domain.user.User
import java.util.*

/**
 * @since 2021-08-10
 */
interface UserRepository {
    fun findByUuid(uuid: UUID): User?

    fun findByNickname(nickname: String): User?

    fun findByEmail(email: String): User?

    fun findByLoginId(loginId: String): User?

    fun save(user: User): User

    fun update(user: User): User
}
