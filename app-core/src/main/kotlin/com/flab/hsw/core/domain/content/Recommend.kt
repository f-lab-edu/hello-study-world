package com.flab.hsw.core.domain.content

import com.flab.hsw.core.domain.content.aggregate.RecommendModel
import java.time.Instant
import java.util.*

interface Recommend {
    val userId: UUID

    val contentId: UUID

    val registeredAt: Instant

    companion object {
        fun create(
            userId : UUID,
            contentId: UUID
        ) : Recommend = RecommendModel.create(
            userId = userId,
            contentId = contentId
        )
    }
}
