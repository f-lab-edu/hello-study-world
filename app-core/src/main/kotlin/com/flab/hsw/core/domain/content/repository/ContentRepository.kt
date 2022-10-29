package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.domain.content.query.Content
import java.util.*

interface ContentRepository {
    fun create(createContent: CreateContentCommand): Content

    fun findByUuid(uuid: UUID): Content?
}
