package com.flab.hsw.core.jdbc.content.repository

import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.domain.content.repository.ContentRecommendationRepository
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import org.springframework.stereotype.Service

@Service
internal class ContentRecommendationRepositoryImpl(
    val contentEntityDao: ContentEntityDao
) : ContentRecommendationRepository {
    override fun findContentRecommendationByUserIdAndContentId(
        recommendation: ContentRecommendation
    ): ContentRecommendation? {
        TODO("Not yet implemented")
    }

    override fun saveContentRecommendation(recommendation: ContentRecommendation): ContentRecommendation {
        TODO("Not yet implemented")
    }
}
