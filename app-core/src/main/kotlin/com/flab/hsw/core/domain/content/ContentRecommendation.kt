package com.flab.hsw.core.domain.content

import com.flab.hsw.core.domain.content.aggregate.ContentRecommendationModel
import java.time.Instant
import java.util.*

interface ContentRecommendation {
    val recommenderUserId: UUID

    val contentId: UUID

    val registeredAt: Instant

    companion object {
        fun create(
            userId : UUID,
            contentId: UUID
        ) : ContentRecommendation = ContentRecommendationModel.create(
            recommenderUserId = userId,
            contentId = contentId
        )
    }
}
