/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.medium

import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.endpoint.health.HealthControllerImpl
import com.flab.hsw.endpoint.v1.content.recommend.CreateContentRecommendationControllerImpl
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.context.ContextConfiguration
import test.domain.user.aggregate.randomUser

@ContextConfiguration(
    classes = [
        HealthControllerImpl::class,
        CreateContentRecommendationControllerImpl::class
    ]
)
class CreateContentRecommendationControllerMediumTestBase : MockMvcMediumTestBase() {

    @MockBean
    protected lateinit var createContentRecommendationUseCase: CreateContentRecommendationUseCase
}
