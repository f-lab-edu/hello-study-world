/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.small

import com.flab.hsw.core.domain.user.User.Companion.LOGIN_ID_REGEX
import com.flab.hsw.core.jdbc.user.UserEntity
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import com.flab.hsw.core.jdbc.user.repository.UserRepositoryImpl
import com.flab.hsw.lib.annotation.SmallTest
import com.github.javafaker.Faker
import com.github.javafaker.service.FakeValuesService
import com.github.javafaker.service.RandomService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import test.domain.user.aggregate.randomUser
import java.util.*

/**
 * @since 2021-08-10
 */
@SmallTest
class UserRepositoryImplSpec {
    private lateinit var sut: UserRepositoryImpl
    private lateinit var usersDao: UserEntityDao

    @BeforeEach
    fun setup() {
        this.usersDao = mock()
        this.sut = UserRepositoryImpl(usersDao)

        `when`(usersDao.insert(any())).thenAnswer { return@thenAnswer it.arguments[0] }
        `when`(usersDao.update(any(), any())).thenAnswer { return@thenAnswer it.arguments[1] }
    }

    @DisplayName("Returns user in cache when meets criteria, rather than invoking DAO operation if:")
    @Nested
    inner class ReturnsCachedUserWhenCacheHit {
        @DisplayName("Cache hit by user id")
        @Test
        fun returnsCachedUserById() {
            // given:
            val uuid = UUID.randomUUID()
            val expectedUser = randomUser(id = uuid)

            // and:
            sut.idToUserCache.put(uuid, expectedUser)

            // when:
            val foundUser = sut.findByUuid(uuid)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(0)).selectByUuid(uuid) }
            )
        }

        @DisplayName("Cache hit by user nickname")
        @Test
        fun returnsCachedUserByNickname() {
            // given:
            val nickname = Faker().name().fullName()
            val expectedUser = randomUser(nickname = nickname)

            // and:
            sut.nicknameToUserCache.put(nickname, expectedUser)

            // when:
            val foundUser = sut.findByNickname(nickname)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(0)).selectByNickname(nickname) }
            )
        }

        @DisplayName("Cache hit by user email")
        @Test
        fun returnsCachedUserByEmail() {
            // given:
            val email = Faker().internet().emailAddress()
            val expectedUser = randomUser(email = email)

            // and:
            sut.emailToUserCache.put(email, expectedUser)

            // when:
            val foundUser = sut.findByEmail(email)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(0)).selectByEmail(email) }
            )
        }

        @DisplayName("Cache hit by user's login id")
        @Test
        fun returnsCachedUserByLoginId() {
            // given:
            val loginId = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(LOGIN_ID_REGEX);
            val expectedUser = randomUser(loginId = loginId)

            // and:
            sut.loginIdToUserCache.put(loginId, expectedUser)

            // when:
            val foundUser = sut.findByLoginId(loginId)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(0)).selectByLoginId(loginId) }
            )
        }
    }

    @DisplayName("Performs DAO operation when user is not found in cache if criteria is:")
    @Nested
    inner class ReturnsUserFromDaoIf {
        @DisplayName("exact uuid")
        @Test
        fun cachedUserByUuidNotFound() {
            // given:
            val uuid = UUID.randomUUID()
            val expectedUser = randomUser(id = uuid)

            // and:
            `when`(usersDao.selectByUuid(uuid)).thenReturn(UserEntity.from(expectedUser))

            // when:
            val foundUser = sut.findByUuid(uuid)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(1)).selectByUuid(uuid) }
            )
        }

        @DisplayName("exact nickname")
        @Test
        fun cachedUserByNicknameNotFound() {
            // given:
            val nickname = Faker().name().fullName()
            val expectedUser = randomUser(nickname = nickname)

            // and:
            `when`(usersDao.selectByNickname(nickname)).thenReturn(UserEntity.from(expectedUser))

            // when:
            val foundUser = sut.findByNickname(nickname)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(1)).selectByNickname(nickname) }
            )
        }

        @DisplayName("exact email")
        @Test
        fun cachedUserByEmailNotFound() {
            // given:
            val email = Faker().internet().emailAddress()
            val expectedUser = randomUser(email = email)

            // and:
            `when`(usersDao.selectByEmail(email)).thenReturn(UserEntity.from(expectedUser))

            // when:
            val foundUser = sut.findByEmail(email)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(1)).selectByEmail(email) }
            )
        }

        @DisplayName("exact loginId")
        @Test
        fun cachedUserByLoginIdNotFound() {
            // given:
            val loginId = FakeValuesService(Locale.ENGLISH, RandomService()).regexify(LOGIN_ID_REGEX);
            val expectedUser = randomUser(loginId = loginId)

            // and:
            `when`(usersDao.selectByLoginId(loginId)).thenReturn(UserEntity.from(expectedUser))

            // when:
            val foundUser = sut.findByLoginId(loginId)

            // then:
            assertAll(
                { assertThat(foundUser, `is`(expectedUser)) },
                { verify(usersDao, times(1)).selectByLoginId(loginId) }
            )
        }
    }

    @DisplayName("Saved user also resides in cache if it is found")
    @Test
    fun savedUserAlsoResidesInCache() {
        // given:
        val user = randomUser()

        // then:
        sut.save(user)

        // expect:
        assertAll(
            { assertThat(sut.idToUserCache.get(user.id), `is`(user)) },
            { assertThat(sut.nicknameToUserCache.get(user.nickname), `is`(user)) },
            { assertThat(sut.emailToUserCache.get(user.email), `is`(user)) },
            { assertThat(sut.loginIdToUserCache.get(user.loginId), `is`(user)) }
        )
    }
}
