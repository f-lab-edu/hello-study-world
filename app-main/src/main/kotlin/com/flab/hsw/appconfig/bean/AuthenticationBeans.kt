package com.flab.hsw.appconfig.bean

import com.flab.hsw.util.JwtTokenManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Configuration
class AuthenticationBeans {

    @Value("\${auth.rsa.public}")
    private lateinit var publicKey: String

    @Value("\${auth.rsa.private}")

    private lateinit var privateKey: String

    @Bean
    fun jwtTokenManager(): JwtTokenManager {
        val keyFactory = KeyFactory.getInstance("RSA")
        return JwtTokenManager(
            publicKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))),
            privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)))
        )
    }
}
