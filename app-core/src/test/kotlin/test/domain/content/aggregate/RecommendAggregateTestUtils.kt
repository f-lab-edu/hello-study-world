package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.aggregate.ContentRecommendationModel
import com.github.javafaker.Faker
import java.util.UUID

fun randomRecommend(
    userId: UUID = UUID.randomUUID(),
    contentId: Long = Faker().number().randomNumber()
): ContentRecommendationModel = ContentRecommendationModel.create(
    recommenderUserId = userId,
    contentId = contentId
)
