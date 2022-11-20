package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.ContentRecommendation

interface ContentRecommendationRepository {
    fun findContentRecommendationByUserIdAndContentId(
        contentRecommendation: ContentRecommendation): ContentRecommendation?

    fun save(contentRecommendation: ContentRecommendation): ContentRecommendation
}
