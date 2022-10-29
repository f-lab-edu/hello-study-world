package com.flab.hsw.core.domain.content.query.aggregate

import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.user.SimpleUserProfile
import java.time.Instant

internal data class ContentModel(
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
