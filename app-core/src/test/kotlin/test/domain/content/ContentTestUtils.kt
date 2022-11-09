package test.domain.content

import com.flab.hsw.core.domain.content.CreateContentCommand
import com.flab.hsw.core.domain.content.usecase.CreateContentUseCase
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.github.javafaker.Faker
import java.util.Locale
import java.util.UUID

fun randomCreateContentMessage(
    url: String = Faker().internet().url(),
    description: String = randomDescriptionIncludingKorean()
): CreateContentUseCase.CreateContentMessage {
    data class FakeCreateContentMessage(
        override val url: String,
        override val description: String
    ) : CreateContentUseCase.CreateContentMessage

    return FakeCreateContentMessage(
        url = url,
        description = description
    )
}

fun randomUrlIncludingKorean(): String {
    return Faker().internet().url() +
            "/" + Faker(Locale.KOREAN).address().city() + "-" + Faker(Locale.KOREAN).lorem().word() +
            "?name=" + Faker(Locale.KOREAN).name().name() +
            "&company=" + Faker(Locale.KOREAN).company().name()
}

fun randomDescriptionIncludingKorean(): String {
    return Faker(Locale.KOREAN).lorem()
        .characters(CreateContentCommand.LENGTH_DESCRIPTION_MIN, CreateContentCommand.LENGTH_DESCRIPTION_MAX)
}

fun randomCreateContentRecommendationMessage(): CreateContentRecommendationUseCase.CreateContentRecommendationMessage {
    return CreateContentRecommendationUseCase.CreateContentRecommendationMessage(
        recommendedContentId = UUID.randomUUID(),
        recommenderId = UUID.randomUUID()
    )
}
