package com.flab.hsw.appconfig.bean

import com.flab.hsw.core.domain.content.repository.ContentRecommendationRepository
import com.flab.hsw.core.domain.content.repository.ContentRepository
import com.flab.hsw.core.domain.content.usecase.CreateContentRecommendationUseCase
import com.flab.hsw.core.domain.user.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ContentRecommendationBeans {

    @Bean
    fun createRecommendUseCase(
        contentRepository: ContentRepository,
        contentRecommendationRepository: ContentRecommendationRepository,
        userRepository: UserRepository
    ) = CreateContentRecommendationUseCase.newInstance(
        contentRepository = contentRepository,
        contentRecommendationRepository = contentRecommendationRepository,
        userRepository = userRepository
    )
}
