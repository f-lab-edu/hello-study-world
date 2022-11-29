package com.flab.hsw.core.jdbc.content

import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.jdbc.JdbcTemplateHelper
import com.flab.hsw.core.jdbc.LongIdentifiable
import com.flab.hsw.core.jdbc.LongIdentifiable.Companion.UNIDENTIFIABLE
import com.flab.hsw.core.jdbc.user.UserEntity
import com.flab.hsw.lib.util.toUUID
import java.time.Instant
import java.util.*

internal class ContentRecommendationEntity(
    val recommenderUserId: UUID,
    val contentId: Long,
    val registeredAt: Instant = Instant.now(),
    override val id: Long = UNIDENTIFIABLE
) : LongIdentifiable {

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is ContentEntity -> false
        else -> this.id == other.id
    }

    override fun hashCode(): Int = Objects.hash(this.id)

    fun toContentRecommendation(): ContentRecommendation = ContentRecommendation.create(
        userId = recommenderUserId,
        contentId = contentId,
    )

    companion object {
        const val TABLE = "content_recommendations"

        const val COL_ID = "id"
        const val COL_RECOMMENDER_USER_ID = "recommender_user_id"
        const val COL_CONTENT_ID = "content_id"
        const val COL_CREATED_AT = "created_at"

        @Suppress("MagicNumber")
        fun from(contentRecommendation: ContentRecommendation): ContentRecommendationEntity =
            with(contentRecommendation) {
                ContentRecommendationEntity(
                    recommenderUserId = this.recommenderUserId,
                    contentId = this.contentId,
                    registeredAt = this.registeredAt
                )
            }

        fun from(
            deserialisationContext: JdbcTemplateHelper,
            map: Map<String, Any?>,
            prefix: String = ""
        ) = with(deserialisationContext) {
            ContentRecommendationEntity(
                id = map[prefix + COL_ID] as Long,
                recommenderUserId = (map[prefix + COL_RECOMMENDER_USER_ID] as ByteArray).toUUID(),
                contentId = map[prefix + COL_CONTENT_ID] as Long,
                registeredAt = map[prefix + COL_CREATED_AT]!!.coerceToInstant()
            )
        }
    }


}
