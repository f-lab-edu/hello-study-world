/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.exception

/**
 * @since 2021-08-10
 */
enum class ErrorCodes(
    val code: Long,
    val description: String
) {
    SERVICE_NOT_FOUND(code = 1, ""),
    WRONG_PRESENTATION(code = 2, ""),
    WRONG_INPUT(code = 7, ""),
    MALFORMED_INPUT(code = 8, ""),
    GENERAL_HTTP_EXCEPTION(199, ""),

    USER_BY_ID_NOT_FOUND(1001, ""),
    USER_BY_EMAIL_DUPLICATED(1002, ""),
    USER_BY_NICKNAME_DUPLICATED(1003, ""),
    USER_BY_LOGIN_ID_DUPLICATED(1004, ""),
    USER_BY_LOGIN_ID_NOT_FOUND(1005, ""),
    USER_PASSWORD_IS_INVALID(1006,""),

    CONTENT_BY_ID_NOT_FOUND(2001,""),
    CONTENT_ONLY_RECOMMENDED_ONCE(2002,""),
    CONTENT_PROVIDER_NOT_FOUND(2003,""),

    UNAUTHORIZED_STATUS_EXCEPTION(3001,""),

    UNHANDLED_EXCEPTION(code = -1, "");

    val asMessageKey: String = "ERROR_${this.name}"

    companion object {
        fun from(code: Any?) =
            values().firstOrNull { it.code == code?.toString()?.toLongOrNull() } ?: UNHANDLED_EXCEPTION
    }
}
