package com.flab.hsw.core.domain.content.aggregate

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.user.User
import java.net.URLDecoder
import java.time.Instant
import java.util.*

internal data class ContentModel(
    override val id: UUID,
    override val url: String,
    override val description: String,
    override val provider: User,
    override val registeredAt: Instant,
    override val lastUpdateAt: Instant,
    override val deleted: Boolean = true
) : Content {
    override fun delete(): Content = this.copy(
        deleted = true,
        lastUpdateAt = Instant.now()
    )

    companion object {
        fun create(
            url: String,
            description: String,
            provider: User
        ): ContentModel {
            val now = Instant.now()

            return ContentModel(
                id = UUID.randomUUID(),
                url = URLDecoder.decode(url, Charsets.UTF_8),
                description = description,
                provider = provider,
                registeredAt = now,
                lastUpdateAt = now,
                deleted = false
            )
        }
    }
}
