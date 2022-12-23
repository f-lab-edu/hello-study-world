package test.endpoint.v1.user

import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

fun returnKeySet(): Pair<PublicKey, PrivateKey> {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(2048)

    val keyPair = keyPairGenerator.genKeyPair()
    return keyPair.public to keyPair.private
}
