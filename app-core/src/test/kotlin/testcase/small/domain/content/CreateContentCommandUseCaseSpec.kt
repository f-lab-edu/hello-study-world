package testcase.small.domain.content

import com.flab.hsw.core.domain.content.query.Content
import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.domain.content.command.repository.ContentCommandRepository
import com.flab.hsw.core.domain.content.usecase.CreateContentUseCase
import com.flab.hsw.core.domain.user.SimpleUserProfile
import com.flab.hsw.lib.annotation.SmallTest
import com.github.javafaker.Faker
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
import java.net.URLEncoder
import java.time.Instant
import java.util.*

@SmallTest
internal class CreateContentCommandUseCaseSpec {
    private lateinit var sut: CreateContentUseCase
    private lateinit var contentCommandRepository: ContentCommandRepository

    @BeforeEach
    fun setup() {
        contentCommandRepository = mock()
        sut = CreateContentUseCase.newInstance(contentCommandRepository)

        `when`(contentCommandRepository.create(any())).thenAnswer {
            val createContentCommand = it.arguments[0] as CreateContentCommand
            val now = Instant.now()
            return@thenAnswer Content.create(
                id = Faker().number().randomNumber(),
                url = createContentCommand.url,
                description = createContentCommand.description,
                provider = SimpleUserProfile.create(
                    id = createContentCommand.providerUserId,
                    nickname = Faker(Locale.KOREAN).name().firstName(),
                    email = Faker().internet().emailAddress()
                ),
                registeredAt = now,
                lastUpdateAt = now,
            )
        }
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


    @DisplayName("If an already readable URL is received, it is returned as is.")
    @Test
    fun urlDoNotNeedToBeDecoded() {
        // given:
        val url = randomUrlIncludingKorean()

        val message = randomCreateContentMessage(url = url)

        // when:
        val createdContent = sut.createContent(UUID.randomUUID(), message)

        // then:
        assertAll(
            { assertThat(createdContent.url, `is`(url)) },
            { assertThat(createdContent.description, `is`(message.description)) }
        )
    }
}
