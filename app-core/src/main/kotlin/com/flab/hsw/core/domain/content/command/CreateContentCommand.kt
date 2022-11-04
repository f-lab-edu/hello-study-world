package com.flab.hsw.core.domain.content.command

import java.net.URLDecoder
import java.util.*

interface CreateContentCommand {
    val url: String
    val description: String
    val providerUserId: UUID
    
    private class CreateContentCommandModel(
        url: String,
        override val description: String,
        override val providerUserId: UUID,
    ) : CreateContentCommand {
        override val url: String = URLDecoder.decode(url, Charsets.UTF_8)
    }

    companion object {
        const val LENGTH_DESCRIPTION_MIN = 1
        const val LENGTH_DESCRIPTION_MAX = 200

        fun create(
            url: String,
            description: String,
            providerUserId: UUID,
        ): CreateContentCommand = CreateContentCommandModel(
            url = url,
            description = description,
            providerUserId = providerUserId,
        )
    }
}
