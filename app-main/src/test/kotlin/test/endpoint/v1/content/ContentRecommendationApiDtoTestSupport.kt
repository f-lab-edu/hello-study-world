/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.endpoint.v1.content

import com.flab.hsw.core.domain.user.User
import com.flab.hsw.endpoint.v1.content.recommend.CreateContentRecommendationRequest
import com.github.javafaker.Faker
import org.springframework.mock.web.MockHttpSession
import test.domain.user.aggregate.randomUser
import java.util.*

fun randomCreateContentRecommendationRequest(
    contentId: UUID = UUID.fromString(Faker().internet().uuid())
) : CreateContentRecommendationRequest = CreateContentRecommendationRequest(contentId)

fun createMockSessionThatContainAuthorizedUser(): MockHttpSession = MockHttpSession().apply {
    this.setAttribute(User.AUTHORIZED_USER_ALIAS, randomUser())
}
