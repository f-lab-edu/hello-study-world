package com.flab.hsw.endpoint.v1.content.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.flab.hsw.core.domain.content.ContentRecommendation
import java.time.Instant
import java.util.*

@JsonSerialize
data class ContentRecommendationResponse(
    @JsonProperty
    val userId: UUID,

    @JsonProperty
    val contentId: Long,

    @JsonProperty
    val registeredAt: Instant
) {
    companion object {
        fun from(contentRecommendation: ContentRecommendation) : ContentRecommendationResponse {
            return ContentRecommendationResponse(
                contentId = contentRecommendation.contentId,
                userId = contentRecommendation.recommenderUserId,
                registeredAt = contentRecommendation.registeredAt
            )
        }
    }
}
