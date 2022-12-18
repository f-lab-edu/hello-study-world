package com.flab.hsw.endpoint.v1.content

import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.endpoint.MustAuthenticated
import com.flab.hsw.endpoint.v1.ApiPathsV1
import com.flab.hsw.endpoint.v1.content.common.ContentRecommendationResponse
import com.flab.hsw.endpoint.v1.content.recommend.CreateContentRecommendationRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * ```
 * POST /v1/contents/recommend
 * Content-Type: application/json
 * body: {
 *      "contentId" : "id Value"
 * }
 * ```
 */
@RequestMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface CreateContentRecommendationController {
    val createRecommendUseCase: CreateContentRecommendationUseCase
    val userRepository: UserRepository

    @MustAuthenticated
    @RequestMapping(
        path = [ApiPathsV1.CONTENT_RECOMMENDATION],
        method = [RequestMethod.POST]
    )
    fun createContentRecommendation(
        servletRequest: HttpServletRequest,
        @RequestBody request: CreateContentRecommendationRequest
    ): ContentRecommendationResponse
}
