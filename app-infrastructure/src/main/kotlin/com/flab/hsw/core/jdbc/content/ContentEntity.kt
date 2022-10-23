package com.flab.hsw.core.jdbc.content

import com.flab.hsw.core.domain.content.CreateContent
import com.flab.hsw.core.jdbc.user.UserEntity
import java.time.Instant
import java.util.*

@SuppressWarnings("LongParameterList")      // Intended complexity to provide various Content creation cases
internal class ContentEntity(
    val uuid: UUID,
    val url: String,
    val description: String,
    val providerUserSeq: Long,
    val registeredAt: Instant,
    val lastActiveAt: Instant,
    val deleted: Boolean
) {
    var seq: Long? = null

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is UserEntity -> false
        else -> this.seq == other.seq
    }

    override fun hashCode(): Int = Objects.hash(this.seq)

    companion object {
        const val TABLE = "contents"

        const val COL_SEQ = "seq"
        const val COL_UUID = "uuid"
        const val COL_URL = "url"
        const val COL_DESCRIPTION = "description"
        const val COL_PROVIDER_USER_SEQ = "provider_user_seq"
        const val COL_CREATED_AT = "created_at"
        const val COL_UPDATED_AT = "updated_at"
        const val COL_DELETED = "deleted"

        fun from(createContent: CreateContent, providerUserSeq: Long): ContentEntity = with(createContent) {
            val now = Instant.now()

            return ContentEntity(
                uuid = id,
                url = url,
                description = description,
                providerUserSeq = providerUserSeq,
                registeredAt = now,
                lastActiveAt = now,
                deleted = false,
            )
        }
    }
}
