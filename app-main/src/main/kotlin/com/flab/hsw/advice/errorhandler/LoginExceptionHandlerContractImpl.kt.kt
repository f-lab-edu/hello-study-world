package com.flab.hsw.advice.errorhandler

import com.flab.hsw.core.exception.KopringException
import com.flab.hsw.exception.UnauthorizedException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.InvalidClaimException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.security.auth.login.CredentialException
import javax.security.auth.login.CredentialNotFoundException
import javax.servlet.http.HttpServletRequest

@Component
internal class JwtExceptionHandlerContractImpl (
    private val log: Logger
) : ExceptionHandlerContract<JwtException>, ErrorCodesToHttpStatusMixin {

    override fun onException(
        req: HttpServletRequest,
        exception: JwtException
    ): Pair<KopringException, HttpStatus>? = when(exception) {
        is ExpiredJwtException -> UnauthorizedException(message = EXPIRED_TOKEN_DESC)
        is InvalidClaimException -> UnauthorizedException(message = INVALID_TOKEN_DESC)
        is MalformedJwtException -> UnauthorizedException(message = MALFORMED_TOKEN_DESC)
        else -> UnauthorizedException()
    }.let { kopringException: KopringException ->
        log.debug("JWT Exception: {}", kopringException.message)
        return kopringException to kopringException.toHttpStatus()
    }

    companion object {
        const val EXPIRED_TOKEN_DESC = "만료된 토큰입니다."
        const val INVALID_TOKEN_DESC = "유효하지 않은 토큰입니다."
        const val MALFORMED_TOKEN_DESC = "잘못된 형식의 토큰입니다."
    }
}

@Component
internal class CredentialExceptionHandlerContractImpl (
    private val log: Logger
) : ExceptionHandlerContract<CredentialException>, ErrorCodesToHttpStatusMixin {

    override fun onException(
        req: HttpServletRequest,
        exception: CredentialException
    ): Pair<KopringException, HttpStatus>? = when(exception) {
        is CredentialNotFoundException -> UnauthorizedException(message = TOKEN_NOT_FOUND_DESC)
        else -> UnauthorizedException()
    }.let { kopringException: KopringException ->
        log.debug("Credential Exception: {}", kopringException.message)
        return kopringException to kopringException.toHttpStatus()
    }

    companion object {
        const val TOKEN_NOT_FOUND_DESC = "토큰이 존재하지 않습니다."
    }
}
