/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.domain.user.exception

import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.external.MalformedInputException
import java.util.*

/**
 * @since 2021-08-10
 */
class UserByLoginIDNotFoundException(
    val id: String,
    override val message: String = "'$id'는 존재하지 않는 아이디입니다.",
    override val cause: Throwable? = null
) : MalformedInputException(ErrorCodes.USER_BY_LOGIN_ID_NOT_FOUND, message, cause) {
    override fun messageArguments(): Collection<String> = setOf(id.toString())
}
