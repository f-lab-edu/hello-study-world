package testcase.medium.endpoint.v1.content

import com.flab.hsw.core.domain.content.exception.ContentByIdNotFoundException
import com.flab.hsw.core.domain.content.exception.ContentRecommendationIsAlreadyExistException
import com.flab.hsw.core.exception.ErrorCodes
import com.flab.hsw.endpoint.v1.ApiPathsV1
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers
import org.junit.Ignore
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import test.endpoint.v1.content.randomCreateContentRecommendationRequest
import test.endpoint.v1.content.createMockSessionThatContainAuthorizedUser
import testcase.medium.CreateContentRecommendationControllerMediumTestBase
import java.util.*

class CreateCreateContentRecommendationRequestSpecRecommendation :
    CreateContentRecommendationControllerMediumTestBase() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @DisplayName("인증되지 않은 사용자가 컨텐츠 추천 기능을 호출하는 경우, 401 Unauthorized 를 반환합니다.")
    @Test
    fun return401UnauthorizedWhenUserIsUnauthorized() {
        // when:
        val request = post(
            endpoint = ApiPathsV1.CONTENT_RECOMMENDATION,
            payload = randomCreateContentRecommendationRequest()
        )

        // then:
        val errorResponse = request.send().expect4xx(HttpStatus.UNAUTHORIZED)

        // expect:
        assertThat(
            ErrorCodes.from(errorResponse.code),
            Matchers.`is`(ErrorCodes.UNAUTHORIZED_STATUS_EXCEPTION)
        )
    }


    @DisplayName("사용자가 입력한 컨텐츠 ID가 존재하지 않는 경우, 404 Not Found 를 반환합니다.")
    @Disabled("테스트 케이스의 수정은 별도 PR로 작성될 예정") @Test
    fun return400BadRequestWhenMalformedInputV2() {
        // given:
        val requestPayload = randomCreateContentRecommendationRequest()

        // and:
        `when`(createContentRecommendationUseCase.createContentRecommendation(any()))
            .thenThrow(ContentByIdNotFoundException(requestPayload.contentId))

        // when:
        val request = request(
            method = HttpMethod.POST,
            endpoint = ApiPathsV1.CONTENT_RECOMMENDATION,
            payload = requestPayload,
            session = createMockSessionThatContainAuthorizedUser()
        )

        // then:
        val errorResponse = request.send().expect4xx(HttpStatus.NOT_FOUND)

        // expert:
        assertThat(
            ErrorCodes.from(errorResponse.code),
            Matchers.`is`(ErrorCodes.CONTENT_BY_ID_NOT_FOUND)
        )
    }


    @DisplayName("사용자가 추천한 컨텐츠가 이미 추천된 경우, 409 Conflict 를 반환합니다.")
    @Disabled("테스트 케이스의 수정은 별도 PR로 작성될 예정") @Test
    fun return409ConflictWhenContentIsAlreadyRecommended() {
        // given:
        val requestPayload = randomCreateContentRecommendationRequest()

        // and:
        `when`(createContentRecommendationUseCase.createContentRecommendation(any()))
            .thenThrow(ContentRecommendationIsAlreadyExistException())

        // when:
        val request = request(
            method = HttpMethod.POST,
            endpoint = ApiPathsV1.CONTENT_RECOMMENDATION,
            payload = requestPayload,
            session = createMockSessionThatContainAuthorizedUser()
        )

        // then:
        val errorResponse = request.send().expect4xx(HttpStatus.CONFLICT)

        // expert:
        assertThat(
            ErrorCodes.from(errorResponse.code),
            Matchers.`is`(ErrorCodes.CONTENT_ONLY_RECOMMENDED_ONCE)
        )
    }
}
