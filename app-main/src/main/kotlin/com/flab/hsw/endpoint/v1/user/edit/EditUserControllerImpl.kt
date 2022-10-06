/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.endpoint.v1.user.edit

import com.flab.hsw.core.domain.user.usecase.EditUserUseCase
import com.flab.hsw.core.exception.external.WrongInputException
import com.flab.hsw.endpoint.v1.user.EditUserController
import com.flab.hsw.endpoint.v1.user.common.UserResponse
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * @since 2021-08-10
 */
@RestController
internal class EditUserControllerImpl(
    private val useCase: EditUserUseCase
) : EditUserController {
    override fun edit(id: UUID, req: EditUserRequest): UserResponse {
        if (req.isEmpty()) {
            throw WrongInputException("null")
        }

        val editedUser = useCase.editUser(id, req)

        return UserResponse.from(editedUser)
    }
}
