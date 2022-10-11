package testcase.small.domain.content

import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.content.usecase.CreateContentUseCase
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.lib.annotation.SmallTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import test.domain.content.randomCreateContentMessage
import test.domain.content.randomUrlIncludingKorean
import test.domain.user.aggregate.randomUser
import java.net.URLEncoder
import java.util.*

@SmallTest
internal class CreateContentUseCaseSpec {
    private lateinit var sut: CreateContentUseCase
    private lateinit var contentRepository: ContentRepository
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        contentRepository = mock()
        userRepository = mock()
        sut = CreateContentUseCase.newInstance(contentRepository, userRepository)

        `when`(contentRepository.save(any())).thenAnswer { return@thenAnswer it.arguments[0] }
        `when`(userRepository.findByUuid(any())).thenAnswer { return@thenAnswer randomUser(id = it.arguments[0] as UUID) }
    }

    @DisplayName("An user object that fully represents message, is created")
    @Test
    fun contentIsCreated() {
        // given:
        val message = randomCreateContentMessage()

        // when:
        val createdContent = sut.createContent(UUID.randomUUID(), message)

        // then:
        assertAll(
            { assertThat(createdContent.url, `is`(message.url)) },
            { assertThat(createdContent.description, `is`(message.description)) }
        )
    }

    @DisplayName("Url including korean must be human readable.")
    @Test
    fun urlIsDecoded() {
        // given:
        val url = randomUrlIncludingKorean()

        val message = randomCreateContentMessage(
            url = URLEncoder.encode(url, Charsets.UTF_8)
        )

        // when:
        val createdContent = sut.createContent(UUID.randomUUID(), message)

        // then:
        assertAll(
            { assertThat(createdContent.url, `is`(url)) },
            { assertThat(createdContent.description, `is`(message.description)) }
        )
    }
}
