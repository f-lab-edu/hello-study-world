package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.aggregate.ContentRecommendationModel
import java.util.UUID

fun randomRecommend(
    userId : UUID = UUID.randomUUID(),
    contentId : UUID = UUID.randomUUID()
) : ContentRecommendationModel = ContentRecommendationModel.create(
    recommenderUserId = userId,
    contentId = contentId
)