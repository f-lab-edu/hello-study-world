package com.flab.hsw.core.jdbc.content.repository

import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.domain.content.command.repository.ContentCommandRepository
import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.jdbc.content.ContentEntity
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import org.springframework.stereotype.Service

@Service
internal class ContentCommandRepositoryImpl(
    private val contentEntityDao: ContentEntityDao,
    private val userEntityDao: UserEntityDao
) : ContentCommandRepository {
    override fun create(createContentCommand: CreateContentCommand): Content {
        val provider = userEntityDao.selectByUuid(createContentCommand.providerUserId)
            ?: throw UserByIdNotFoundException(createContentCommand.providerUserId)

        return contentEntityDao.insert(ContentEntity.from(createContentCommand, provider.seq!!))
            .toContent(provider)
    }
}
