package com.flab.hsw.core.domain.content.aggregate

import com.flab.hsw.core.domain.content.ContentRecommendation
import java.time.Instant
import java.util.UUID

data class ContentRecommendationModel(
    override val recommenderUserId: UUID,
    override val contentId: Long,
    override val registeredAt: Instant
) : ContentRecommendation {
    companion object {
        fun create(
            recommenderUserId: UUID,
            contentId: Long
        ): ContentRecommendationModel = ContentRecommendationModel(
            recommenderUserId = recommenderUserId,
            contentId = contentId,
            registeredAt = Instant.now()
        )
    }
}
