package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.domain.content.aggregate.ContentRecommendationModel
import com.github.javafaker.Faker
import java.util.UUID

fun randomContentRecommend(
    recommenderUserId: UUID = UUID.randomUUID(),
    contentId: Long = Faker().number().randomNumber()
): ContentRecommendation = ContentRecommendationModel.create(
    recommenderUserId = recommenderUserId,
    contentId = contentId
)
