package testcase.small.domain.content

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.exception.ContentByIdNotFoundException
import com.flab.hsw.core.domain.content.exception.ContentRecommendationIsAlreadyExistException
import com.flab.hsw.core.domain.content.repository.ContentRecommendationRepository
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.lib.annotation.SmallTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import test.domain.content.aggregate.randomGeneratedNow
import test.domain.content.randomCreateContentRecommendationMessage
import test.domain.user.aggregate.randomUser
import java.util.*

@SmallTest
internal class CreateContentRecommendationUseCaseSpec {

    private lateinit var sut: CreateContentRecommendationUseCase
    private lateinit var contentRepository: ContentRepository
    private lateinit var contentRecommendationRepository: ContentRecommendationRepository
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        contentRepository = mock()
        contentRecommendationRepository = mock()
        userRepository = mock()
        sut = CreateContentRecommendationUseCase.newInstance(
            contentRepository,
            contentRecommendationRepository,
            userRepository
        )
    }

    @DisplayName("사용자가 컨텐츠를 추천 시, 추천 히스토리에 추천 객체가 추가됩니다.")
    @Test
    fun recommendOfContentIsCreated() {
        // given:
        val message = randomCreateContentRecommendationMessage()

        // and:
        `when`(userRepository.findByUuid(any())).thenAnswer { return@thenAnswer randomUser() }
        `when`(contentRepository.findById(any())).thenAnswer { return@thenAnswer Content.randomGeneratedNow() }
        `when`(contentRecommendationRepository.findByUserIdAndContentId(any()))
            .thenAnswer { return@thenAnswer null }
        `when`(contentRecommendationRepository.save(any()))
            .thenAnswer { return@thenAnswer it.arguments[0] }

        // when:
        val recommend = sut.createContentRecommendation(message)

        // then:
        assertAll(
            { assertThat(message.recommenderId, `is`(recommend.recommenderUserId)) },
            { assertThat(message.recommendedContentId, `is`(recommend.contentId)) },
            { verify(contentRecommendationRepository, times(1)).save(any()) }
        )
    }

    @DisplayName("전달된 회원 id가 존재하지 않는 경우, 예외가 발생합니다.")
    @Test
    fun errorWhenUserIdIsNotFound() {
        // given:
        val message = randomCreateContentRecommendationMessage()

        // and:
        `when`(userRepository.findByUuid(any())).thenAnswer { return@thenAnswer null }

        // then:
        assertThrows<UserByIdNotFoundException> { sut.createContentRecommendation(message) }
    }

    @DisplayName("전달된 컨텐츠 id가 존재하지 않는 경우, 예외가 발생합니다.")
    @Test
    fun errorWhenContentIdIsNotFound() {
        // given:
        val message = randomCreateContentRecommendationMessage()

        // and:
        `when`(userRepository.findByUuid(any())).thenAnswer { return@thenAnswer randomUser() }
        `when`(contentRepository.findById(any())).thenAnswer { return@thenAnswer null }

        // then:
        assertThrows<ContentByIdNotFoundException> { sut.createContentRecommendation(message) }
    }

    @DisplayName("동일한 회원이 동일한 컨텐츠를 1회 초과 추천한 경우, 예외가 발생합니다.")
    @Test
    fun errorWhenContentIsRecommendedTwice() {
        // given:
        val message = randomCreateContentRecommendationMessage()

        // and:
        `when`(userRepository.findByUuid(any())).thenAnswer { return@thenAnswer randomUser() }
        `when`(contentRepository.findById(any())).thenAnswer { return@thenAnswer Content.randomGeneratedNow() }
        `when`(contentRecommendationRepository.findByUserIdAndContentId(any()))
            .thenAnswer { return@thenAnswer it.arguments[0] }

        // then:
        assertThrows<ContentRecommendationIsAlreadyExistException> { sut.createContentRecommendation(message) }
    }
}
