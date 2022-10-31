package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.aggregate.RecommendModel
import java.util.UUID

fun randomRecommend(
    userId : UUID = UUID.randomUUID(),
    contentId : UUID = UUID.randomUUID()
) : RecommendModel = RecommendModel.create(
    userId = userId,
    contentId = contentId
)