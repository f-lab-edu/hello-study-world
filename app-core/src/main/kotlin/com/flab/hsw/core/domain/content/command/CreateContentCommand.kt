package com.flab.hsw.core.domain.content.command

import com.flab.hsw.core.domain.content.command.aggregate.CreateContentCommandModel
import java.util.*

interface CreateContentCommand {
    val url: String
    val description: String
    val providerUserId: UUID

    companion object {
        const val LENGTH_DESCRIPTION_MIN = 1
        const val LENGTH_DESCRIPTION_MAX = 200

        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various Content creation cases
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
