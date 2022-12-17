package com.flab.hsw.appconfig.bean

import com.flab.hsw.util.JwtTokenManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Configuration
@PropertySource("classpath:static/keyFile.properties")
class AuthenticationBeans {

    @Value("\${jwt.key.encoded.public}")
    private lateinit var publicKey: String

    @Value("\${jwt.key.encoded.private}")
    private lateinit var privateKey: String

    @Bean
    fun jwtTokenManager(): JwtTokenManager {
        val keyFactory = KeyFactory.getInstance("RSA")
        return JwtTokenManager(
            publicKey = keyFactory
                .generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))),
            privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)))
        )
    }
}
