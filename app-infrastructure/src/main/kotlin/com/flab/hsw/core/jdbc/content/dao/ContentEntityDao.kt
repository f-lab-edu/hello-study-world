package com.flab.hsw.core.jdbc.content.dao

import com.flab.hsw.core.jdbc.JdbcTemplateHelper
import com.flab.hsw.core.jdbc.content.ContentEntity
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

internal interface ContentEntityDao {
    fun insert(contentEntity: ContentEntity): ContentEntity
    fun selectById(id: Long): ContentEntity?
}

@Repository
internal class ContentEntityDaoImpl(
    override val jdbcTemplate: JdbcTemplate
) : JdbcTemplateHelper(), ContentEntityDao {
    override fun insert(contentEntity: ContentEntity): ContentEntity {
        val sql = """
            INSERT INTO `${ContentEntity.TABLE}` (
                `${ContentEntity.COL_URL}`,
                `${ContentEntity.COL_DESCRIPTION}`,
                `${ContentEntity.COL_PROVIDER_USER_ID}`,
                `${ContentEntity.COL_DELETED}`,
                `${ContentEntity.COL_CREATED_AT}`,
                `${ContentEntity.COL_UPDATED_AT}`
            )
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()

        @Suppress("MagicNumber")    // Not a magic number in this context
        return contentEntity.copy(
            id = super.doInsertAndGetId(ContentEntity.COL_ID, sql) {
                setStringEx(1, contentEntity.url)
                setStringEx(2, contentEntity.description)
                setLongEx(3, contentEntity.providerUserId)
                setBooleanEx(4, contentEntity.deleted)
                setTimestampEx(5, contentEntity.registeredAt)
                setTimestampEx(6, contentEntity.lastUpdateAt)
            }.key!!.toLong()
        )
    }

    override fun selectById(id: Long): ContentEntity? {
        val sql = """
            SELECT *
            FROM `${ContentEntity.TABLE}` u
            WHERE u.`${ContentEntity.COL_ID}` = ?
              AND u.`${ContentEntity.COL_DELETED}` = FALSE
        """.trimIndent()

        return selectOne(sql, id)
    }

    private fun selectOne(sql: String, vararg args: Any): ContentEntity? {
        val users = selectMany(sql, *args)

        return when (users.size) {
            0 -> null
            1 -> users[0]
            else -> throw IncorrectResultSizeDataAccessException(1, users.size)
        }
    }

    private fun selectMany(sql: String, vararg args: Any): List<ContentEntity> =
        jdbcTemplate.queryForList(sql, *args).map { ContentEntity.from(this, it) }
}
