package com.flab.hsw.core.jdbc.content.repository

import com.flab.hsw.core.annotation.InfrastructureService
import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.domain.content.repository.ContentRecommendationRepository
import com.flab.hsw.core.jdbc.content.ContentRecommendationEntity
import com.flab.hsw.core.jdbc.content.dao.ContentRecommendationEntityDao

@Service
internal class ContentRecommendationRepositoryImpl(
    val contentRecommendationEntityDao: ContentRecommendationEntityDao
) : ContentRecommendationRepository {
    override fun findContentRecommendationByUserIdAndContentId(
        contentRecommendation: ContentRecommendation
    ): ContentRecommendation? {
        TODO("Not yet implemented")
    }

        TODO("Not yet implemented")
    override fun save(contentRecommendation: ContentRecommendation): ContentRecommendation {
    }
}
