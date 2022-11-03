package com.flab.hsw.core.domain.content.aggregate

import com.flab.hsw.core.domain.content.Recommend
import java.time.Instant
import java.util.UUID

data class RecommendModel(
    override val userId: UUID,
    override val contentId: UUID,
    override val registeredAt: Instant
) : Recommend {
    companion object {
        fun create(
            userId: UUID,
            contentId: UUID
        ): RecommendModel = RecommendModel(
            userId = userId,
            contentId = contentId,
            registeredAt = Instant.now()
        )
    }
}
