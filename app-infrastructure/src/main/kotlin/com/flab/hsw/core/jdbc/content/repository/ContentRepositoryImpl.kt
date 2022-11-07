package com.flab.hsw.core.jdbc.content.repository

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class ContentRepositoryImpl(
    val contentEntityDao: ContentEntityDao
) : ContentRepository {
    override fun save(content: Content): Content {
        TODO("Not yet implemented")
    }

    override fun findByUuid(uuid: UUID): Content? {
        TODO("Not yet implemented")
    }
}
