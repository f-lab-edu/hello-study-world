/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package testcase.medium

import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.endpoint.health.HealthControllerImpl
import com.flab.hsw.endpoint.v1.content.recommend.CreateContentRecommendationControllerImpl
import com.flab.hsw.util.JwtTokenManager
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(
    classes = [
        HealthControllerImpl::class,
        CreateContentRecommendationControllerImpl::class,
    ]
)
class CreateContentRecommendationControllerMediumTestBase : MockMvcMediumTestBase() {

    @MockBean
    protected lateinit var createContentRecommendationUseCase: CreateContentRecommendationUseCase

    @MockBean
    protected lateinit var userRepository: UserRepository

    @MockBean
    protected lateinit var jwtTokenManager: JwtTokenManager
}
