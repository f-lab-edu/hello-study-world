package test.endpoint.v1.user

import com.flab.hsw.util.JwtTokenManager.Companion.AUTHORIZATION_HEADER
import com.github.javafaker.Faker
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.restassured.response.Response
import java.security.*
import java.security.spec.X509EncodedKeySpec
import java.time.Instant
import java.util.*

fun decodeKeyFrom(
    keyString: String,
): Key = KeyFactory.getInstance("RSA")
    .generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(keyString)))

fun getAuthorizationHeaderFrom(
    response: Response
): String = response.headers.get(AUTHORIZATION_HEADER).value

fun getClaimsFrom(
    issuedToken: String,
    publicKey: Key,
): Jws<Claims>? {
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
