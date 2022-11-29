/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.appconfig.bean

import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.core.domain.user.usecase.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @since 2021-08-10
 */
@Configuration
class UserBeans {
    @Bean
    fun createUserUseCase(
        userRepository: UserRepository,
    ) = CreateUserUseCase.newInstance(
        userRepository,
    )

    @Bean
    fun getUserUseCase(
        userRepository: UserRepository
    ) = FindUserUseCase.newInstance(
        userRepository
    )

    @Bean
    fun editUserUseCase(
        userRepository: UserRepository
    ) = EditUserUseCase.newInstance(
        userRepository
    )

    @Bean
    fun deleteUserUseCase(
        userRepository: UserRepository
    ) = DeleteUserUseCase.newInstance(
        userRepository
    )

    @Bean
    fun userLoginUseCase(
        userRepository: UserRepository
    ) = UserLoginUseCase.newInstance(
        userRepository
    )
}
