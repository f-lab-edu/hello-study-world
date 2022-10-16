/*
 * kopringboot-multimodule-template
 * Sir.LOIN Intellectual property. All rights reserved.
 */
package test.domain.user

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.jdbc.user.UserEntity
import com.github.javafaker.Faker
import com.github.javafaker.service.FakeValuesService
import com.github.javafaker.service.RandomService
import test.domain.user.aggregate.randomUser
import java.time.Instant
import java.util.*

internal fun randomUserEntity(
    id: UUID = UUID.randomUUID(),
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress(),
    loginId: String = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.LOGIN_ID_REGEX),
    password: String = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(User.PASSWORD_REGEX),
    registeredAt: Instant = Instant.now(),
    lastActiveAt: Instant = Instant.now(),
    deleted: Boolean = false
): UserEntity = UserEntity.from(
    randomUser(
        id = id,
        nickname = nickname,
        email = email,
        loginId = loginId,
        password = password,
        registeredAt = registeredAt,
        lastActiveAt = lastActiveAt,
        deleted = deleted
    )
)
