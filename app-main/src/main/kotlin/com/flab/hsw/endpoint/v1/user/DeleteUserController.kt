/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.endpoint.v1.user

import com.flab.hsw.endpoint.common.response.SimpleResponse
import com.flab.hsw.endpoint.v1.ApiPathsV1
import com.flab.hsw.endpoint.v1.ApiVariableV1
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.util.*

/**
 * ```
 * DELETE /v1/users/{id}
 *
 * Content-Type: application/json
 * ```
 *
 * @since 2021-08-10
 */
@RequestMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
interface DeleteUserController {
    @RequestMapping(
        path = [ApiPathsV1.USERS_ID],
        method = [RequestMethod.DELETE]
    )
    fun delete(@PathVariable(ApiVariableV1.ID) id: UUID): SimpleResponse<Boolean>
}
