package testcase.small

import com.flab.hsw.core.domain.content.command.CreateContentCommand
import com.flab.hsw.core.domain.user.exception.UserByIdNotFoundException
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import com.flab.hsw.core.jdbc.content.repository.ContentCommandRepositoryImpl
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import com.flab.hsw.lib.annotation.SmallTest
import com.github.javafaker.Faker
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import test.domain.content.aggregate.randomCreateContentCommand
import test.domain.user.randomUserEntity
import java.util.*

@SmallTest
class ContentCommandRepositoryImplSpec {
    private lateinit var sut: ContentCommandRepositoryImpl
    private lateinit var contentsDao: ContentEntityDao
    private lateinit var usersDao: UserEntityDao

    @BeforeEach
    fun setup() {
        this.contentsDao = mock()
        this.usersDao = mock()
        this.sut = ContentCommandRepositoryImpl(contentsDao, usersDao)

        `when`(contentsDao.insert(any())).thenAnswer { return@thenAnswer it.arguments[0] }
    }

    @DisplayName("컨텐츠를 생성 시 제공자의 ID를 DAO에서 찾을 수 없으면 예외를 발생시킵니다..")
    @Test
    fun errorIfUserNotFoundWhenCreatingContent() {
        // given:
        val contentProviderUserUuid = UUID.randomUUID()

        // and:
        `when`(usersDao.selectByUuid(contentProviderUserUuid)).thenReturn(null)

        // then:
        assertThrows<UserByIdNotFoundException> {
            sut.create(
                createContentCommand = CreateContentCommand.randomCreateContentCommand(providerUserId = contentProviderUserUuid)
            )
        }
    }

    @DisplayName("컨텐츠 생성 성공 시 컨텐츠 모델을 반환합니다.")
    @Test
    fun successWhenCreatingContent() {
        // given:
        val contentProviderUser = randomUserEntity(id = UUID.randomUUID()).apply {
            seq = Faker().number().randomNumber()
        }
        val newContent = CreateContentCommand.randomCreateContentCommand(providerUserId = contentProviderUser.uuid)

        // and:
        `when`(usersDao.selectByUuid(contentProviderUser.uuid)).thenReturn(contentProviderUser)

        // when:
        val createdContent = sut.create(newContent)

        // then:
        assertAll(
            { assertThat(createdContent.id, `is`(newContent.id)) },
            { assertThat(createdContent.url, `is`(newContent.url)) },
            { assertThat(createdContent.description, `is`(newContent.description)) },
            { assertThat(createdContent.provider.id, `is`(newContent.providerUserId)) },
            { assertThat(createdContent.provider.id, `is`(contentProviderUser.uuid)) },
            { assertThat(createdContent.provider.email, `is`(contentProviderUser.email)) },
            { assertThat(createdContent.provider.nickname, `is`(contentProviderUser.nickname)) },
        )
    }
}
