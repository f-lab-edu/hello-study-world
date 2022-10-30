package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.user.SimpleUserProfile
import com.github.javafaker.Faker
import test.domain.content.randomDescriptionIncludingKorean
import test.domain.content.randomUrlIncludingKorean
import java.time.Instant
import java.util.*

fun CreateContentCommand.Companion.random(
    url: String = randomUrlIncludingKorean(),
    description: String = randomDescriptionIncludingKorean(),
    providerUserId: UUID = UUID.randomUUID()
): CreateContentCommand = create(
    url = url,
    description = description,
    providerUserId = providerUserId
)


fun Content.Companion.randomGeneratedNow(
    seq: Long = Faker().number().randomNumber(),
    url: String = randomUrlIncludingKorean(),
    description: String = randomDescriptionIncludingKorean(),
    provider: SimpleUserProfile = SimpleUserProfile.create(
        id = UUID.randomUUID(),
        nickname = Faker(Locale.KOREAN).name().firstName(),
        email = Faker().internet().emailAddress()
    ),
): Content {
    val now = Instant.now()

    return create(
        seq = seq,
        url = url,
        description = description,
        provider = provider,
        registeredAt = now,
        lastUpdateAt = now,
    )
}
