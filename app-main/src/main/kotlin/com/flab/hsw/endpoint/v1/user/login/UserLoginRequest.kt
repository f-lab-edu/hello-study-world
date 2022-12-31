package com.flab.hsw.endpoint.v1.user.login

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.usecase.UserLoginUseCase
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

@JsonDeserialize
data class UserLoginRequest(

    @NotEmpty
    @field:Pattern(regexp = User.LOGIN_ID_REGEX)
    @JsonProperty
    override val loginId: String,

    @NotEmpty
    @field:Pattern(regexp = User.PASSWORD_REGEX)
    @JsonProperty
    override val password: String
) : UserLoginUseCase.UserLoginMessage

