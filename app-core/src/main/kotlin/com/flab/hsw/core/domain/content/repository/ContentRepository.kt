package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.CreateContentCommand
import java.util.*

interface ContentRepository {
    fun create(createContentCommand: CreateContentCommand): Content

    fun findByUuid(uuid: UUID): Content?
}
