package com.flab.hsw.endpoint.auth;

import com.flab.hsw.util.JwtTokenManager
import com.flab.hsw.endpoint.ApiPaths
import com.flab.hsw.endpoint.common.response.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface AuthenticationController {
    @RequestMapping(
        path = [ApiPaths.PUBLIC_KEY],
        method = [RequestMethod.GET]
    )
    fun key(): SimpleResponse<String>
}

@RestController
internal class AuthenticationControllerImpl : AuthenticationController {
    @Autowired private lateinit var jwtTokenManager: JwtTokenManager

    override fun key() = SimpleResponse(jwtTokenManager.returnEncodedPublicKey())
}
