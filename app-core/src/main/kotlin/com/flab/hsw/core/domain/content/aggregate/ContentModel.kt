package com.flab.hsw.core.domain.content.aggregate

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.user.UserProfile
import java.time.Instant
import java.util.*

internal data class ContentModel(
    override val id: UUID,
    override val url: String,
    override val description: String,
    override val providerUserProfile: UserProfile,
    override val registeredAt: Instant,
    override val lastUpdateAt: Instant,
    override val deleted: Boolean,
) : Content {
    override fun delete(): Content = this.copy(
        deleted = true,
        lastUpdateAt = Instant.now()
    )

    companion object {
        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various Content creation cases
        fun create(
            id: UUID,
            url: String,
            description: String,
            providerUserProfile: UserProfile,
            registeredAt: Instant,
            lastUpdateAt: Instant,
            deleted: Boolean,
        ): ContentModel = ContentModel(
            id = id,
            url = url,
            description = description,
            providerUserProfile = providerUserProfile,
            registeredAt = registeredAt,
            lastUpdateAt = lastUpdateAt,
            deleted = deleted,
        )
    }
}
