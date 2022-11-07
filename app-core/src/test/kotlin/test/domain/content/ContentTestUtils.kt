package test.domain.content

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.usecase.CreateContentUseCase
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.github.javafaker.Faker
import java.util.*

fun randomCreateContentMessage(
    url: String = Faker().internet().url(),
    description: String = Faker(Locale.KOREAN).lorem()
        .characters(Content.LENGTH_DESCRIPTION_MIN, Content.LENGTH_DESCRIPTION_MAX)
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

fun randomCreateContentRecommendationMessage(): CreateContentRecommendationUseCase.CreateContentRecommendationMessage {
    return CreateContentRecommendationUseCase.CreateContentRecommendationMessage(
        recommendedContentId = UUID.randomUUID(),
        recommenderId = UUID.randomUUID()
    )
}
