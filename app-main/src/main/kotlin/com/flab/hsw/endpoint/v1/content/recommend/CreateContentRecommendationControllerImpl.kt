package com.flab.hsw.endpoint.v1.content.recommend

import com.flab.hsw.core.exception.external.UnAuthorizedExecutingException
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase.CreateContentRecommendationMessage
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.endpoint.v1.content.CreateContentRecommendationController
import com.flab.hsw.endpoint.v1.content.common.ContentRecommendationResponse
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

@RestController
internal class CreateContentRecommendationControllerImpl(
    override val createRecommendUseCase: CreateContentRecommendationUseCase
) : CreateContentRecommendationController {
    override fun createContentRecommendation(
        session: HttpSession,
        request: CreateContentRecommendationRequest
    ): ContentRecommendationResponse = ContentRecommendationResponse.from(
        createRecommendUseCase.createContentRecommendation(
            CreateContentRecommendationMessage(
                recommendedContentId = request.contentId,
                recommenderId = findAuthorizedUserIn(session)?.id ?: throw UnAuthorizedExecutingException()
            )
        )
    )
}

private fun findAuthorizedUserIn(session: HttpSession): User? =
    session.getAttribute(User.AUTHORIZED_USER_ALIAS) as User?
