/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.endpoint.v1.user.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.flab.hsw.core.domain.user.User
import java.time.Instant
import java.util.*

/**
 * @since 2021-08-10
 */
@JsonSerialize
data class UserResponse(
    @JsonProperty
    @JsonPropertyDescription(DESC_ID)
    val id: UUID,

    @JsonProperty
    @JsonPropertyDescription(DESC_NICKNAME)
    val nickname: String,

    @JsonProperty
    @JsonPropertyDescription(DESC_EMAIL)
    val email: String,

    @JsonProperty
    @JsonPropertyDescription(DESC_LOGIN_ID)
    val loginId: String,

    @JsonProperty
    @JsonPropertyDescription(DESC_REGISTERED_AT)
    val registeredAt: Instant,

    @JsonProperty
    @JsonPropertyDescription(DESC_LAST_ACTIVE_AT)
    val lastActiveAt: Instant
) {
    companion object {
        const val DESC_ID = "This is user's id."
        const val DESC_NICKNAME = "This is user's nickname."
        const val DESC_EMAIL = "This is user's email address"
        const val DESC_LOGIN_ID = "This is user's id for login."
        const val DESC_REGISTERED_AT = "This is time that user is registered as member of service."
        const val DESC_LAST_ACTIVE_AT = "This is time that user's last active."

        fun from(src: User) = with(src) {
            UserResponse(
                id = id,
                nickname = nickname,
                email = email,
                loginId = loginId,
                registeredAt = registeredAt,
                lastActiveAt = lastActiveAt
            )
        }
    }
}
