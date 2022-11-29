package testcase.medium.jdbc.content

import com.flab.hsw.core.domain.content.aggregate.ContentRecommendationModel
import com.flab.hsw.core.jdbc.content.ContentEntity
import com.flab.hsw.core.jdbc.content.ContentRecommendationEntity
import com.flab.hsw.core.jdbc.content.dao.ContentEntityDao
import com.flab.hsw.core.jdbc.content.dao.ContentRecommendationEntityDao
import com.flab.hsw.core.jdbc.user.UserEntity
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import test.domain.content.random
import test.domain.user.randomUserEntity
import testcase.medium.JdbcTemplateMediumTestBase
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentRecommendationEntityDaoTest : JdbcTemplateMediumTestBase() {

    @Autowired
    private lateinit var sut: ContentRecommendationEntityDao

    @Autowired
    private lateinit var userEntityDao: UserEntityDao

    @Autowired
    private lateinit var contentEntityDao: ContentEntityDao

    private lateinit var preparedUser: UserEntity;
    private lateinit var preparedContent: ContentEntity;

    @BeforeAll
    fun setup() {
        preparedUser = userEntityDao.insert(randomUserEntity())
        preparedContent = contentEntityDao.insert(ContentEntity.random(providerUserId = preparedUser.id))
    }

    @DisplayName("사용자의 컨텐츠 추천이 정상적으로 생성된 이후, 생성된 추천 히스토리를 검색할 수 있습니다.")
    @Test
    fun insertAndSelectByUserIdAndContentIdTest() {
        // given:
        val randomContentRecommend = ContentRecommendationModel.create(
            recommenderUserId = preparedUser.uuid,
            contentId = preparedContent.id
        )

        // when:
        val savedContentRecommendation = sut.insert(ContentRecommendationEntity.from(randomContentRecommend))

        // then:
        val foundContentRecommendation = sut.selectByUserIdAndContentId(
            userId = randomContentRecommend.recommenderUserId,
            contentId = randomContentRecommend.contentId
        )

        assertAll(
            { assertThat(savedContentRecommendation.recommenderUserId, `is`(foundContentRecommendation?.recommenderUserId))},
            { assertThat(savedContentRecommendation.contentId, `is`(foundContentRecommendation?.contentId))},
        )
    }
}