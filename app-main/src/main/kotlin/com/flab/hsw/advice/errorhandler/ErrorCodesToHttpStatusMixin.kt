/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.advice.errorhandler

import com.flab.hsw.core.domain.user.exception.UserByLoginIDNotFoundException
import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.core.exception.ErrorCodes.*
import com.flab.hsw.core.exception.KopringException
import com.flab.hsw.exception.GeneralHttpException
import org.springframework.http.HttpStatus

/**
 * @since 2021-08-10
 */
interface ErrorCodesToHttpStatusMixin {
    fun KopringException.toHttpStatus() = if (this is GeneralHttpException) {
        this.statusCode
    } else {
        this.codeBook.toHttpStatus()
    }

    fun ErrorCodes.toHttpStatus(): HttpStatus = when (this) {
        // General error cases
        SERVICE_NOT_FOUND -> HttpStatus.NOT_FOUND
        WRONG_PRESENTATION -> HttpStatus.UNSUPPORTED_MEDIA_TYPE
        WRONG_INPUT, MALFORMED_INPUT -> HttpStatus.BAD_REQUEST
        GENERAL_HTTP_EXCEPTION -> HttpStatus.BAD_REQUEST

        // Domain error cases to HTTP status
        USER_BY_ID_NOT_FOUND, USER_BY_LOGIN_ID_NOT_FOUND -> HttpStatus.NOT_FOUND
        USER_BY_EMAIL_DUPLICATED, USER_BY_NICKNAME_DUPLICATED, USER_BY_LOGIN_ID_DUPLICATED -> HttpStatus.CONFLICT

        CONTENT_BY_ID_NOT_FOUND, CONTENT_PROVIDER_NOT_FOUND -> HttpStatus.NOT_FOUND
        CONTENT_ONLY_RECOMMENDED_ONCE -> HttpStatus.CONFLICT

        UNAUTHORIZED_STATUS_EXCEPTION -> HttpStatus.UNAUTHORIZED

        UNHANDLED_EXCEPTION -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}
