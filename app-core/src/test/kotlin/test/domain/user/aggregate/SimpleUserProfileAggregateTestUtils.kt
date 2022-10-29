package test.domain.user.aggregate

import com.flab.hsw.core.domain.user.SimpleUserProfile
import com.github.javafaker.Faker
import java.util.*

fun SimpleUserProfile.Companion.random(
    id: UUID = UUID.randomUUID(),
    nickname: String = Faker().name().fullName(),
    email: String = Faker().internet().emailAddress()
) = create(
    id = id,
    nickname = nickname,
    email = email
)
