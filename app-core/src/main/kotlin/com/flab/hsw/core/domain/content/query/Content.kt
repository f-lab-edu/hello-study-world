package com.flab.hsw.core.domain.content.query

import com.flab.hsw.core.domain.SoftDeletable
import com.flab.hsw.core.domain.user.SimpleUserProfile
import java.time.Instant

interface Content : SoftDeletable {
    val id: Long
    val url: String
    val description: String
    val provider: SimpleUserProfile
    val registeredAt: Instant
    val lastUpdateAt: Instant

    private data class ContentModel(
        override val id: Long,
        override val url: String,
        override val description: String,
        override val provider: SimpleUserProfile,
        override val registeredAt: Instant,
        override val lastUpdateAt: Instant,
        override val deleted: Boolean,
    ) : Content {
        override fun delete(): Content = this.copy(
            deleted = true,
            lastUpdateAt = Instant.now()
        )
    }

    companion object {
        @SuppressWarnings("LongParameterList")      // Intended complexity to provide various Content creation cases
        fun create(
            id: Long,
            url: String,
            description: String,
            provider: SimpleUserProfile,
            registeredAt: Instant,
            lastUpdateAt: Instant,
        ): Content {
            return ContentModel(
                id = id,
                url = url,
                description = description,
                provider = provider,
                registeredAt = registeredAt,
                lastUpdateAt = lastUpdateAt,
                deleted = false,
            )
        }
    }
}
