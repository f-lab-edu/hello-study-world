package testcase.small.interceptor

import com.flab.hsw.core.domain.user.User.Companion.AUTHORIZED_USER_ID_ALIAS
import com.flab.hsw.interceptor.AuthenticationInterceptor
import com.flab.hsw.lib.annotation.SmallTest
import com.flab.hsw.util.JwtTokenManager
import com.flab.hsw.util.JwtTokenManager.Companion.AUTHORIZATION_HEADER
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import test.endpoint.v1.user.parseToken
import test.endpoint.v1.user.returnKeySet
import test.endpoint.v1.user.createTokenWithRandomName
import test.interceptor.returnMustAuthenticatedHandlerMethod
import test.interceptor.returnNonAuthenticatedHandlerMethod
import java.security.PrivateKey
import java.security.PublicKey
import javax.security.auth.login.CredentialNotFoundException

@SmallTest
class AuthenticationInterceptorSpec {

    private lateinit var sut: AuthenticationInterceptor
    private lateinit var jwtTokenManager: JwtTokenManager

    private val keyPair: Pair<PublicKey, PrivateKey> = returnKeySet()

    @BeforeEach
    fun setup(){
        jwtTokenManager = mock()
        sut = AuthenticationInterceptor(jwtTokenManager)
    }

    @DisplayName("인증 대상이 아닌 핸들러를 호출한 경우, true를 반환합니다.")
    @Test
    fun successWhenHandlerDontNeedAuthentication(){
        // when:
        val isAuthenticationHandler = sut.preHandle(
            MockHttpServletRequest(),
            MockHttpServletResponse(),
            returnNonAuthenticatedHandlerMethod()
        )

        // then:
        assertThat(isAuthenticationHandler, `is`(true))
    }

    @DisplayName("토큰 전달 및 검증이 정상적인 경우, HttpServletRequest `authorizedUserId` 애트리뷰트에 토큰의 subject가 저장됩니다.")
    @Test
    fun successWhenNormalCase(){
        // given:
        val preparedToken = createTokenWithRandomName(keyPair.second)
        val parsedPreparedToken = parseToken(preparedToken, keyPair.first)
        `when` (jwtTokenManager.validAndReturnClaims(any())).thenReturn(parsedPreparedToken)

        // and:
        val requestWithAuthorizationHeader = MockHttpServletRequest()
        requestWithAuthorizationHeader.addHeader(AUTHORIZATION_HEADER, preparedToken)

        // when:
        val isInterceptorPassed = sut.preHandle(
            requestWithAuthorizationHeader,
            MockHttpServletResponse(),
            returnMustAuthenticatedHandlerMethod())

        // then:
        assertAll(
            { assertThat(isInterceptorPassed, `is`(true)) },
            { assertThat(requestWithAuthorizationHeader.getAttribute(AUTHORIZED_USER_ID_ALIAS),
                `is`(parsedPreparedToken?.body?.subject)) }
        )
    }

    @DisplayName("Authorization Header가 비어있는 경우, CredentialNotFoundException 예외가 반환됩니다.")
    @Test
    fun failWhenAuthorizationHeaderIsEmpty(){
        // when & then:
        verify(jwtTokenManager, never()).validAndReturnClaims(any())
        assertThrows<CredentialNotFoundException> {
            sut.preHandle(
                MockHttpServletRequest(),
                MockHttpServletResponse(),
                returnMustAuthenticatedHandlerMethod())
        }
    }
}