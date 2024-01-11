/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.jdbc.user

import com.flab.hsw.core.domain.user.SimpleUserProfile
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.jdbc.JdbcTemplateHelper
import com.flab.hsw.core.jdbc.LongIdentifiable
import com.flab.hsw.core.jdbc.LongIdentifiable.Companion.UNIDENTIFIABLE
import com.flab.hsw.lib.util.toUUID
import java.time.Instant
import java.util.*
import javax.persistence.*

/**
 * [equals] and [hashCode] implementation is inspired by the article as follows:
 * [Martin Fowler's blog: EvansClassification](https://martinfowler.com/bliki/EvansClassification.html)
 *
 * @since 2021-08-10
 */
@SuppressWarnings("LongParameterList")  // 별도의 클래스로 추출할 프로퍼티가 존재하지 않습니다.
@Entity
@Table(
    name = "USERS",
    uniqueConstraints = [UniqueConstraint(name = "uk_users_identity", columnNames = ["nickname", "email"])]
)
internal class UserEntity(

    @Column(unique = true, columnDefinition = "BINARY(16)")
    val uuid: UUID,

    var nickname: String,

    @Column(unique = true)
    var email: String,

    var loginId: String,
    var password: String,
    var createdAt: Instant,
    var lastActiveAt: Instant,
    var deleted: Boolean
) : LongIdentifiable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    override var id: Long = UNIDENTIFIABLE

    var version: Long = 0L

    fun toUser(): User = User.create(
        id = this.uuid,
        nickname = this.nickname,
        email = this.email,
        loginId = this.loginId,
        password = this.password,
        createdAt = this.createdAt,
        lastActiveAt = this.lastActiveAt,
        deleted = this.deleted
    )

    fun toUserProfile(): SimpleUserProfile = SimpleUserProfile.create(
        id = this.uuid,
        nickname = this.nickname,
        email = this.email
    )

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is UserEntity -> false
        else -> this.id == other.id
    }

    override fun hashCode(): Int = Objects.hash(this.id)

    companion object {
        const val TABLE = "users"

        const val COL_ID = "id"
        const val COL_UUID = "uuid"
        const val COL_NICKNAME = "nickname"
        const val COL_EMAIL = "email"
        const val COL_LOGIN_ID = "login_Id"
        const val COL_PASSWORD = "password"
        const val COL_DELETED = "deleted"
        const val COL_CREATED_AT = "created_at"
        const val COL_UPDATED_AT = "updated_at"
        const val COL_VERSION = "version"

        fun from(user: User): UserEntity = with(user) {
            UserEntity(
                uuid = id,
                nickname = nickname,
                email = email,
                loginId = loginId,
                password = password,
                createdAt = createdAt,
                lastActiveAt = lastActiveAt,
                deleted = deleted
            )
        }

        fun from(
            deserialisationContext: JdbcTemplateHelper,
            map: Map<String, Any?>,
            prefix: String = ""
        ) = with(deserialisationContext) {
            UserEntity(
                uuid = (map[prefix + COL_UUID] as ByteArray).toUUID(),
                nickname = map[prefix + COL_NICKNAME] as String,
                email = map[prefix + COL_EMAIL] as String,
                loginId = map[prefix + COL_LOGIN_ID] as String,
                password = map[prefix + COL_PASSWORD] as String,
                createdAt = map[prefix + COL_CREATED_AT]!!.coerceToInstant(),
                lastActiveAt = map[prefix + COL_UPDATED_AT]!!.coerceToInstant(),
                deleted = map[prefix + COL_DELETED] as Boolean
            ).apply {
                this.id = map[prefix + COL_ID] as Long
                this.version = map[prefix + COL_VERSION] as Long
            }
        }
    }
}
