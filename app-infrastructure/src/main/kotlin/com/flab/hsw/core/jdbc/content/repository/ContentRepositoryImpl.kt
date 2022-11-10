package com.flab.hsw.core.jdbc.content.repository

import com.flab.hsw.core.annotation.InfrastructureService
import com.flab.hsw.core.domain.content.CreateContentCommand
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.exception.ContentProviderNotFoundException
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.jdbc.content.ContentEntity
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao

@InfrastructureService
internal class ContentRepositoryImpl(
    private val contentEntityDao: ContentEntityDao,
    private val userEntityDao: UserEntityDao
) : ContentRepository {
    override fun create(createContentCommand: CreateContentCommand): Content {
        val provider = userEntityDao.selectByUuid(createContentCommand.providerUserId)
            ?: throw UserByIdNotFoundException(createContentCommand.providerUserId)

        return contentEntityDao.insert(ContentEntity.from(createContentCommand, provider.id))
            .toContent(provider)
    }

    override fun findById(id: Long): Content? {
        return contentEntityDao.selectById(id)?.let { contentEntity ->
            val provider = userEntityDao.selectById(contentEntity.providerUserSeq)
                ?: throw ContentProviderNotFoundException(id)

            contentEntity.toContent(provider)
        }
    }
}
