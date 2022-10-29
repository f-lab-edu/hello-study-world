package com.flab.hsw.core.domain.content.query.aggregate

import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.user.SimpleUserProfile
import com.flab.hsw.core.domain.user.User
import java.time.Instant
import java.util.*

internal data class ContentModel(
    override val id: UUID,
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

    companion object {
        fun create(
            id: UUID = UUID.randomUUID(),
            url: String,
            description: String,
            provider: User,
            registeredAt: Instant? = null,
            lastUpdateAt: Instant? = null,
            deleted: Boolean = false
        ): ContentModel {
            val now = Instant.now()

            return ContentModel(
                id = id,
                url = url,
                description = description,
                provider = provider,
                registeredAt = registeredAt ?: now,
                lastUpdateAt = lastUpdateAt ?: now,
                deleted = deleted
            )
        }
    }
}
