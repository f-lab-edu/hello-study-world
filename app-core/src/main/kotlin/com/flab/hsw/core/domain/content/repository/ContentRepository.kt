package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.CreateContentCommand

interface ContentRepository {
    fun create(createContentCommand: CreateContentCommand): Content

    fun findById(id: Long): Content?
}
