package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.ContentRecommendation

interface ContentRecommendationRepository {

    fun findContentRecommendationByUserIdAndContentId(recommendation: ContentRecommendation): ContentRecommendation?

    fun saveContentRecommendation(recommendation: ContentRecommendation): ContentRecommendation
}
