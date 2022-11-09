package com.flab.hsw.core.jdbc.content.repository

import com.flab.hsw.core.annotation.InfrastructureService
import com.flab.hsw.core.domain.content.CreateContentCommand
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.jdbc.content.ContentEntity
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import java.util.UUID

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

    override fun findByUuid(uuid: UUID): Content? {
        TODO("Not yet implemented")
    }
}
