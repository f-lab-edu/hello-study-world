/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.endpoint.v1.user.create

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.User.Companion.LOGIN_ID_REGEX
import com.flab.hsw.core.domain.user.User.Companion.PASSWORD_REGEX
import com.flab.hsw.core.domain.user.usecase.CreateUserUseCase
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

/**
 * @since 2021-08-10
 */
@JsonDeserialize
data class CreateUserRequest(
    @field:NotEmpty
    @field:Size(min = User.LENGTH_NICKNAME_MIN, max = User.LENGTH_NICKNAME_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_NAME)
    override val nickname: String,

    @field:NotEmpty
    @field:Email
    @field:Size(max = User.LENGTH_EMAIL_MAX)
    @JsonProperty
    @JsonPropertyDescription(DESC_EMAIL)
    override val email: String,

    @field:NotEmpty
    @field:Pattern(regexp = LOGIN_ID_REGEX)
    @JsonProperty
    @JsonPropertyDescription(DESC_LOGIN_ID)
    override val loginId: String,

    @field:NotEmpty
    @field:Pattern(regexp = PASSWORD_REGEX)
    @JsonProperty
    @JsonPropertyDescription(DESC_PASSWORD)
    override val password: String
): CreateUserUseCase.CreateUserMessage{
    companion object {
        const val DESC_EMAIL = "This is user's email address"
        const val DESC_NAME = "This is user's name."
        const val DESC_LOGIN_ID = "This is user's id for login."
        const val DESC_PASSWORD = "This is user's password."
    }
}
