package com.flab.hsw.core.domain.content

import com.flab.hsw.core.domain.SoftDeletable
import com.flab.hsw.core.domain.content.aggregate.ContentModel
import com.flab.hsw.core.domain.user.UserProfile
import java.time.Instant
import java.util.*

interface Content : SoftDeletable {
    val id: UUID
    val url: String
    val description: String
    val providerUserProfile: UserProfile
    val registeredAt: Instant
    val lastUpdateAt: Instant

    companion object {
        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various Content creation cases
        fun create(
            id: UUID,
            url: String,
            description: String,
            providerUserProfile: UserProfile,
            registeredAt: Instant,
            lastUpdateAt: Instant,
        ): Content {
            return ContentModel.create(
                id = id,
                url = url,
                description = description,
                providerUserProfile = providerUserProfile,
                registeredAt = registeredAt,
                lastUpdateAt = lastUpdateAt,
                deleted = false,
            )
        }
    }
}
