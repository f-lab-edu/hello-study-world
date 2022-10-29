package com.flab.hsw.core.domain.content.command.repository

import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.content.command.CreateContentCommand

interface ContentCommandRepository {
    fun create(createContentCommand: CreateContentCommand): Content
}
