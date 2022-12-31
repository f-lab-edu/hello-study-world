/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.endpoint.v1.content

import com.flab.hsw.endpoint.v1.content.recommend.CreateContentRecommendationRequest
import com.github.javafaker.Faker

fun randomCreateContentRecommendationRequest(
    contentId: Long = Faker().number().randomNumber(),
) : CreateContentRecommendationRequest = CreateContentRecommendationRequest(contentId)