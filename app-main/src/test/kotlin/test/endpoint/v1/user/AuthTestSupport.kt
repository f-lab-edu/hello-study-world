package test.endpoint.v1.user

import com.flab.hsw.endpoint.v1.user.login.UserLoginResponse
import com.github.javafaker.Faker
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import java.security.Key
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.time.Instant
import java.util.*

fun getAuthorizedTokenFrom(
    response: UserLoginResponse
): String = response.authorizedToken

fun getClaimsFrom(
    issuedToken: String,
    publicKey: Key,
): Jws<Claims> {
    return Jwts.parserBuilder()
        .setSigningKey(publicKey)
        .build()
        .parseClaimsJws(issuedToken)
}

fun createTokenWithRandomName(
    key: PrivateKey
): String = Jwts.builder()
    .setSubject(Faker.instance().name().name())
    .setIssuedAt(Date.from(Instant.now()))
    .signWith(key)
    .compact()

fun parseToken(
    token: String,
    key: PublicKey
): Jws<Claims>? = Jwts.parserBuilder()
    .setSigningKey(key)
    .build()
    .parseClaimsJws(token)

fun returnKeySet(): Pair<PublicKey, PrivateKey> {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(2048)

    val keyPair = keyPairGenerator.genKeyPair()
    return keyPair.public to keyPair.private
}
