package com.flab.hsw.core.jdbc.content.repository

import com.flab.hsw.core.annotation.InfrastructureService
import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.domain.content.repository.ContentRecommendationRepository
import com.flab.hsw.core.jdbc.content.ContentRecommendationEntity
import com.flab.hsw.core.jdbc.content.dao.ContentRecommendationEntityDao

@InfrastructureService
internal class ContentRecommendationRepositoryImpl(
    val contentRecommendationEntityDao: ContentRecommendationEntityDao
) : ContentRecommendationRepository {
    override fun findContentRecommendationByUserIdAndContentId(
        contentRecommendation: ContentRecommendation
    ): ContentRecommendation? {
        return contentRecommendationEntityDao.selectByUserIdAndContentId(
            userId = contentRecommendation.recommenderUserId,
            contentId = contentRecommendation.contentId
        )?.toContentRecommendation()
    }

    override fun save(contentRecommendation: ContentRecommendation): ContentRecommendation {
        return contentRecommendationEntityDao.insert(ContentRecommendationEntity.from(contentRecommendation))
            .toContentRecommendation()
    }
}
