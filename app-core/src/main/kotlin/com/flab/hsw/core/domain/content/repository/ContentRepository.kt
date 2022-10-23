package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.CreateContent
import java.util.*

interface ContentRepository {
    fun create(createContent: CreateContent): Content

    fun findByUuid(uuid: UUID): Content?
}
