package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.Recommend
import java.util.*

interface ContentRepository {
    fun save(content: Content): Content

    fun findByUuid(uuid: UUID): Content?

    fun findRecommendHistoryByUserIdAndContentId(recommend: Recommend): Recommend?

    fun saveRecommendHistory(recommend: Recommend): Recommend
}
