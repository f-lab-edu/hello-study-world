package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.aggregate.ContentModel
import com.flab.hsw.core.domain.user.User
import com.github.javafaker.Faker
import test.domain.user.aggregate.randomUser
import java.time.Instant
import java.util.*

fun randomContent(
    url: String = Faker().internet().url(),
    description: String = Faker().lorem().word(),
    provider: User = randomUser()
): Content = ContentModel.create(
    id = UUID.randomUUID(),
    url = url,
    description = description,
    providerUserProfile = provider,
    registeredAt = Instant.now(),
    lastUpdateAt = Instant.now(),
    deleted = false,
)
