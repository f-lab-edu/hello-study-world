package testcase.medium.jdbc.content

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.CreateContentCommand
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import com.flab.hsw.core.jdbc.content.repository.ContentRepositoryImpl
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import com.github.javafaker.Faker
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import test.domain.content.aggregate.random
import test.domain.user.randomUserEntity
import testcase.medium.JdbcTemplateMediumTestBase
import java.util.*

class ContentRepositorySpec : JdbcTemplateMediumTestBase() {
    private lateinit var sut: ContentRepository

    @Autowired
    private lateinit var contentsDao: ContentEntityDao

    @Autowired
    private lateinit var usersDao: UserEntityDao

    @BeforeEach
    fun setup() {
        this.sut = ContentRepositoryImpl(contentsDao, usersDao)
    }

    @DisplayName("컨텐츠를 생성 시 제공자의 ID를 DAO에서 찾을 수 없으면 예외를 발생시킵니다..")
    @Test
    fun errorIfUserNotFoundWhenCreatingContent() {
        // given:
        val contentProviderUserUuid = UUID.randomUUID()

        // then:
        assertThrows<UserByIdNotFoundException> {
            sut.create(
                createContentCommand = CreateContentCommand.random(providerUserId = contentProviderUserUuid)
            )
        }
    }

    @DisplayName("컨텐츠 생성 성공 시 컨텐츠 모델을 반환합니다.")
    @Test
    fun successWhenCreatingContent() {
        // given:
        val contentProviderUser = usersDao.insert(
            userEntity = randomUserEntity(id = UUID.randomUUID()).apply {
                id = Faker().number().randomNumber()
            }
        )

        // when:
        val newContent = CreateContentCommand.random(providerUserId = contentProviderUser.uuid)
        val createdContent: Content = sut.create(newContent)

        // then:
        assertAll(
            { assertThat(createdContent.id, not(Matchers.nullValue())) },
            { assertThat(createdContent.url, `is`(newContent.url)) },
            { assertThat(createdContent.description, `is`(newContent.description)) },
            { assertThat(createdContent.provider.id, `is`(newContent.providerUserId)) },
            { assertThat(createdContent.provider.id, `is`(contentProviderUser.uuid)) },
            { assertThat(createdContent.provider.email, `is`(contentProviderUser.email)) },
            { assertThat(createdContent.provider.nickname, `is`(contentProviderUser.nickname)) },
        )
    }
}
