package com.flab.hsw.core.jdbc.content.dao

import com.flab.hsw.core.domain.content.ContentRecommendation
import com.flab.hsw.core.jdbc.JdbcTemplateHelper
import com.flab.hsw.core.jdbc.content.ContentRecommendationEntity
import com.flab.hsw.lib.util.toByteArray
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

internal interface ContentRecommendationEntityDao {
    fun insert(contentRecommendationEntity: ContentRecommendationEntity): ContentRecommendationEntity

    fun selectByUserIdAndContentId(userId: UUID, contentId: Long): ContentRecommendationEntity?
}

@Repository
internal class ContentRecommendationEntityDaoImpl(
    override val jdbcTemplate: JdbcTemplate
) : JdbcTemplateHelper(), ContentRecommendationEntityDao {
    override fun insert(contentRecommendationEntity: ContentRecommendationEntity): ContentRecommendationEntity {
        val sql = """
            INSERT INTO `${ContentRecommendationEntity.TABLE}` (
                `${ContentRecommendationEntity.COL_RECOMMENDER_USER_ID}`,
                `${ContentRecommendationEntity.COL_CONTENT_ID}`,
                `${ContentRecommendationEntity.COL_CREATED_AT}`
            ) 
            VALUES (?,?,?)
        """.trimIndent()

        @Suppress("MagicNumber")
        return contentRecommendationEntity.apply {
            super.doInsertAndGetId(ContentRecommendationEntity.COL_ID, sql) {
                setBinaryEx(1, contentRecommendationEntity.recommenderUserId.toByteArray())
                setLongEx(2, contentRecommendationEntity.contentId)
                setTimestampEx(3, contentRecommendationEntity.registeredAt)
            }.key!!.toLong()
        }
    }

    override fun selectByUserIdAndContentId(userId: UUID, contentId: Long): ContentRecommendationEntity? {
        val sql = """
            SELECT *
            FROM `${ContentRecommendationEntity.TABLE}` cr
            WHERE cr.`${ContentRecommendationEntity.COL_RECOMMENDER_USER_ID}` = ?
              AND cr.`${ContentRecommendationEntity.COL_CONTENT_ID}` = ?
        """.trimIndent()

        return selectOne(sql, userId, contentId)
    }

    private fun selectOne(sql: String, vararg args: Any): ContentRecommendationEntity? {
        val contentRecommendations = selectMany(sql, *args)

        return when (contentRecommendations.size) {
            0 -> null
            1 -> contentRecommendations[0]
            else -> throw IncorrectResultSizeDataAccessException(1, contentRecommendations.size)
        }
    }

    private fun selectMany(sql: String, vararg args: Any): List<ContentRecommendationEntity> =
        jdbcTemplate.queryForList(sql, *args).map { ContentRecommendationEntity.from(this, it) }
}
