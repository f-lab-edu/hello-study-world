package com.flab.hsw.core.jdbc.content.dao

import com.flab.hsw.core.jdbc.JdbcTemplateHelper
import com.flab.hsw.core.jdbc.content.ContentEntity
import com.flab.hsw.core.jdbc.user.UserEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

internal interface ContentEntityDao {
    fun insert(contentEntity: ContentEntity): ContentEntity
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
                `${ContentEntity.COL_PROVIDER_USER_SEQ}`,
                `${ContentEntity.COL_DELETED}`,
                `${ContentEntity.COL_CREATED_AT}`,
                `${ContentEntity.COL_UPDATED_AT}`
            )
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()

        @Suppress("MagicNumber")    // Not a magic number in this context
        return contentEntity.copy(
            seq = super.doInsertAndGetId(UserEntity.COL_SEQ, sql) {
                setStringEx(1, contentEntity.url)
                setStringEx(2, contentEntity.description)
                setLongEx(3, contentEntity.providerUserSeq)
                setBooleanEx(4, contentEntity.deleted)
                setTimestampEx(5, contentEntity.registeredAt)
                setTimestampEx(6, contentEntity.lastActiveAt)
            }.key!!.toLong()
        )
    }
}
