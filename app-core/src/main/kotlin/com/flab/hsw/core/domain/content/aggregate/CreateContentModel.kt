package com.flab.hsw.core.domain.content.aggregate

import com.flab.hsw.core.domain.content.CreateContent
import java.net.URLDecoder
import java.util.*

internal data class CreateContentModel(
    override val id: UUID,
    override val url: String,
    override val description: String,
    override val providerUserId: UUID,
) : CreateContent {
    companion object {
        fun create(
            id: UUID,
            url: String,
            description: String,
            providerUserId: UUID,
        ): CreateContentModel = CreateContentModel(
            id = id,
            url = URLDecoder.decode(url, Charsets.UTF_8),
            description = description,
            providerUserId = providerUserId,
        )
    }
}
