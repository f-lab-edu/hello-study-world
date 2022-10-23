package testcase.medium.jdbc.content

import com.flab.hsw.core.jdbc.content.ContentEntity
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import test.domain.content.randomContentEntity
import testcase.medium.JdbcTemplateMediumTestBase
import testcase.medium.MediumTestDataDeclarations.randomUuidFromUserEntityData

class ContentEntityDaoTest : JdbcTemplateMediumTestBase() {
    @Autowired
    private lateinit var sut: ContentEntityDao

    @Autowired
    private lateinit var userEntityDao: UserEntityDao

    @DisplayName("")
    @Test
    fun insertTest() {
        // given:
        val user = userEntityDao.selectByUuid(randomUuidFromUserEntityData())!!

        val randomContent: ContentEntity = randomContentEntity(providerUserSeq = user.seq!!)

        // then:
        val savedContent = sut.insert(randomContent)

        // expect:
        assertThat(savedContent.uuid, not(Matchers.nullValue()))
    }
}
