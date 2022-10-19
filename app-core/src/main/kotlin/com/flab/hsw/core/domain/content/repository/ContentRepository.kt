package com.flab.hsw.core.domain.content.repository

import com.flab.hsw.core.domain.content.Content

interface ContentRepository {
    fun save(content: Content): Content
}
