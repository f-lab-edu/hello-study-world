package com.flab.hsw.core.jdbc.content.dao

import com.flab.hsw.core.jdbc.JdbcTemplateHelper
import com.flab.hsw.core.jdbc.content.ContentEntity
import com.flab.hsw.core.jdbc.user.UserEntity
import com.flab.hsw.lib.util.toByteArray
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
                `${ContentEntity.COL_UUID}`,
                `${ContentEntity.COL_URL}`,
                `${ContentEntity.COL_DESCRIPTION}`,
                `${ContentEntity.COL_PROVIDER_USER_SEQ}`,
                `${ContentEntity.COL_DELETED}`,
                `${ContentEntity.COL_CREATED_AT}`,
                `${ContentEntity.COL_UPDATED_AT}`
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        return contentEntity.apply {
            @Suppress("MagicNumber")    // Not a magic number in this context
            seq = super.doInsertAndGetId(UserEntity.COL_SEQ, sql) {
                setBinaryEx(1, contentEntity.uuid.toByteArray())
                setStringEx(2, contentEntity.url)
                setStringEx(3, contentEntity.description)
                setLongEx(4, contentEntity.providerUserSeq)
                setBooleanEx(5, contentEntity.deleted)
                setTimestampEx(6, contentEntity.registeredAt)
                setTimestampEx(7, contentEntity.lastActiveAt)
            }.key!!.toLong()
        }
    }
}
