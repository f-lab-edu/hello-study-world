package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.content.query.aggregate.ContentModel
import com.flab.hsw.core.domain.user.User
import com.github.javafaker.Faker
import test.domain.content.randomDescriptionIncludingKorean
import test.domain.content.randomUrlIncludingKorean
import test.domain.user.aggregate.randomUser
import java.time.Instant
import java.util.*

fun CreateContentCommand.Companion.randomCreateContentCommand(
    url: String = randomUrlIncludingKorean(),
    description: String = randomDescriptionIncludingKorean(),
    providerUserId: UUID = UUID.randomUUID()
): CreateContentCommand = create(
    encodedUrl = url,
    description = description,
    providerUserId = providerUserId
)

fun randomContent(
    url: String = Faker().internet().url(),
    description: String = Faker().lorem().word(),
    provider: User = randomUser()
): Content = ContentModel.create(
    id = UUID.randomUUID(),
    url = url,
    description = description,
    provider = provider,
    registeredAt = Instant.now(),
    lastUpdateAt = Instant.now(),
    deleted = false,
)
