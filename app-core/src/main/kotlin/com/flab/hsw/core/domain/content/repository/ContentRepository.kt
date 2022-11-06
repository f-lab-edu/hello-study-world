package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.ContentRecommendation
import java.util.*

interface ContentRepository {
    fun save(content: Content): Content

    fun findByUuid(uuid: UUID): Content?

    fun findContentRecommendationByUserIdAndContentId(
        contentRecommendation: ContentRecommendation
    ): ContentRecommendation?

    fun saveContentRecommendation(contentRecommendation: ContentRecommendation): ContentRecommendation
}
