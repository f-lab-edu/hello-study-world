package testcase.small.util

import com.flab.hsw.lib.annotation.SmallTest
import com.flab.hsw.util.JwtTokenManager
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import test.domain.user.aggregate.randomUser
import test.endpoint.v1.user.returnKeySet

@SmallTest
class JwtTokenManagerSpec {

    private lateinit var sut: JwtTokenManager

    @BeforeEach
    fun setup() {
        val keyPair = returnKeySet()
        sut = JwtTokenManager(
            publicKey = keyPair.first,
            privateKey = keyPair.second,
            accessTokenExpirePeriod = 7200L,
            refreshTokenExpirePeriod = 1209600L
        )
    }

    @DisplayName("정상적으로 토큰이 생성된 경우, 회원의 loginId가 subject에 주입됩니다.")
    @Test
    fun tokenIsCreated() {
        // given:
        val randomUser = randomUser()

        // when:
        val returnClaimsBody = sut.validAndReturnClaims(sut.createAccessTokenBy(randomUser.loginId)).body

        // then:
       assertThat(returnClaimsBody?.subject, `is`(randomUser.loginId))
    }
}