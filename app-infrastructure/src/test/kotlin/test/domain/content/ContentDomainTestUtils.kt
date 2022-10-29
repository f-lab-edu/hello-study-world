package test.domain.content

import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.jdbc.content.ContentEntity
import com.github.javafaker.Faker
import java.time.Instant
import java.util.*

internal fun randomContentEntity(
    url: String = randomUrlIncludingKorean(),
    description: String = Faker(Locale.KOREAN).lorem()
        .characters(CreateContentCommand.LENGTH_DESCRIPTION_MIN, CreateContentCommand.LENGTH_DESCRIPTION_MAX),
    providerUserSeq: Long = Faker().number().randomNumber(),
    registeredAt: Instant = Instant.now(),
    lastActiveAt: Instant = Instant.now(),
    deleted: Boolean = false,
    seq: Long = Faker().number().randomNumber(),
): ContentEntity = ContentEntity(
    url = url,
    description = description,
    providerUserSeq = providerUserSeq,
    registeredAt = registeredAt,
    lastActiveAt = lastActiveAt,
    deleted = deleted,
    seq = seq
)
