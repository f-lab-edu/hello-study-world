package com.flab.hsw.advice.errorhandler

import com.flab.hsw.core.exception.KopringException
import com.flab.hsw.exception.UnauthorizedException
import com.flab.hsw.exception.UnauthorizedException.Companion.EXPIRED_TOKEN_DESC
import com.flab.hsw.exception.UnauthorizedException.Companion.INVALID_TOKEN_DESC
import com.flab.hsw.exception.UnauthorizedException.Companion.TOKEN_NOT_FOUND_DESC
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.InvalidClaimException
import io.jsonwebtoken.JwtException
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.security.SignatureException
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
        else -> UnauthorizedException()
    }.let { kopringException: KopringException ->
        log.debug("JWT Exception: {}", kopringException.message)
        return kopringException to kopringException.toHttpStatus()
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
}
