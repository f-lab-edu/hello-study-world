package com.flab.hsw.core.domain.content

import com.flab.hsw.core.domain.content.aggregate.CreateContentModel
import java.util.*

interface CreateContent {
    val id: UUID
    val url: String
    val description: String
    val providerUserId: UUID

    companion object {
        const val LENGTH_DESCRIPTION_MIN = 1
        const val LENGTH_DESCRIPTION_MAX = 200

        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various Content creation cases
        fun create(
            id: UUID = UUID.randomUUID(),
            url: String,
            description: String,
            providerUserId: UUID,
        ): CreateContent = CreateContentModel.create(
            id = id,
            url = url,
            description = description,
            providerUserId = providerUserId,
        )
    }
}
