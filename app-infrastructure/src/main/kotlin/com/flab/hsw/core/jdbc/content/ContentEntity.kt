package com.flab.hsw.core.jdbc.content

import com.flab.hsw.core.domain.content.CreateContentCommand
import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.jdbc.JdbcTemplateHelper
import com.flab.hsw.core.jdbc.LongIdentifiable
import com.flab.hsw.core.jdbc.LongIdentifiable.Companion.UNIDENTIFIABLE
import com.flab.hsw.core.jdbc.user.UserEntity
import java.time.Instant
import java.util.*

@SuppressWarnings("LongParameterList")      // Intended complexity to provide various Content creation cases
internal class ContentEntity(
    var url: String,
    var description: String,
    val providerUserId: Long,
    val registeredAt: Instant,
    var lastUpdateAt: Instant,
    val deleted: Boolean,
    override val id: Long = UNIDENTIFIABLE
) : LongIdentifiable {
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is ContentEntity -> false
        else -> this.id == other.id
    }

    override fun hashCode(): Int = Objects.hash(this.id)

    fun copy(
        url: String = this.url,
        description: String = this.description,
        providerUserId: Long = this.providerUserId,
        registeredAt: Instant = this.registeredAt,
        lastActiveAt: Instant = this.lastUpdateAt,
        deleted: Boolean = this.deleted,
        id: Long = this.id
    ): ContentEntity = ContentEntity(
        url = url,
        description = description,
        providerUserId = providerUserId,
        registeredAt = registeredAt,
        lastUpdateAt = lastActiveAt,
        deleted = deleted,
        id = id
    )

    fun toContent(providerUserEntity: UserEntity): Content {
        return Content.create(
            id = id,
            url = url,
            description = description,
            provider = providerUserEntity.toUserProfile(),
            registeredAt = registeredAt,
            lastUpdateAt = lastUpdateAt,
        )
    }

    companion object {
        const val TABLE = "contents"

        const val COL_ID = "id"
        const val COL_URL = "url"
        const val COL_DESCRIPTION = "description"
        const val COL_PROVIDER_USER_ID = "provider_user_id"
        const val COL_CREATED_AT = "created_at"
        const val COL_UPDATED_AT = "updated_at"
        const val COL_DELETED = "deleted"

        fun from(createContentCommand: CreateContentCommand, providerUserId: Long): ContentEntity =
            with(createContentCommand) {
                val now = Instant.now()

                return ContentEntity(
                    url = url,
                    description = description,
                    providerUserId = providerUserId,
                    registeredAt = now,
                    lastUpdateAt = now,
                    deleted = false,
                )
            }

        fun from(
            deserialisationContext: JdbcTemplateHelper,
            map: Map<String, Any?>,
        ) = with(deserialisationContext) {
            ContentEntity(
                url = map[COL_URL] as String,
                description = map[COL_DESCRIPTION] as String,
                providerUserId = map[COL_PROVIDER_USER_ID] as Long,
                registeredAt = map[COL_CREATED_AT]!!.coerceToInstant(),
                lastUpdateAt = map[COL_UPDATED_AT]!!.coerceToInstant(),
                deleted = map[COL_DELETED] as Boolean,
                id = map[COL_ID] as Long,
            )
        }
    }
}
