/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.endpoint.v1.user.delete

import com.flab.hsw.core.domain.user.usecase.DeleteUserUseCase
import com.flab.hsw.endpoint.common.response.SimpleResponse
import com.flab.hsw.endpoint.v1.user.DeleteUserController
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * @since 2021-08-10
 */
@RestController
internal class DeleteUserControllerImpl(
    private val useCase: DeleteUserUseCase
) : DeleteUserController {
    override fun delete(id: UUID): SimpleResponse<Boolean> {
        useCase.deleteUserById(id)

        return SimpleResponse(true)
    }
}
