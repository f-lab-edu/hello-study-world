/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package test.endpoint.v1.user

import com.flab.hsw.endpoint.ApiPaths
import com.flab.hsw.endpoint.common.response.SimpleResponse
import com.flab.hsw.endpoint.v1.ApiPathsV1
import com.flab.hsw.endpoint.v1.user.common.UserResponse
import com.flab.hsw.endpoint.v1.user.create.CreateUserRequest
import com.flab.hsw.endpoint.v1.user.edit.EditUserRequest
import com.flab.hsw.endpoint.v1.user.login.UserLoginRequest
import io.restassured.RestAssured.request
import io.restassured.response.Response
import org.springframework.restdocs.payload.FieldDescriptor
import test.endpoint.v1.usersId
import testcase.large.RestAssuredLargeTestBase
import testcase.large.endpoint.EndpointLargeTestBase
import java.net.URI
import java.util.*

fun EndpointLargeTestBase.createUserApi(
    requestPayload: CreateUserRequest,
    requestFields: List<FieldDescriptor>? = null,
    responseFields: List<FieldDescriptor>? = null,
): Response {
    return request()
        .apply {
            if (requestFields != null && responseFields != null) {
                this.withDocumentation(URI.create(ApiPathsV1.USERS).toRelativePath(), requestFields, responseFields)
            }
        }
        .body(requestPayload)
        .post(ApiPathsV1.USERS)
}

fun EndpointLargeTestBase.getUserApi(
    userId: UUID,
    responseFields: List<FieldDescriptor>? = null,
): Response {
    return request()
        .apply {
            if (responseFields != null) {
                this.withDocumentation(URI.create(ApiPathsV1.USERS_ID).toRelativePath(), null, responseFields)
            }
        }
        .get(ApiPathsV1.usersId(userId))
}

fun EndpointLargeTestBase.editUserApi(
    userId: UUID,
    requestPayload: EditUserRequest,
    requestFields: List<FieldDescriptor>? = null,
    responseFields: List<FieldDescriptor>? = null,
): Response {
    return request()
        .apply {
            if (responseFields != null) {
                this.withDocumentation(URI.create(ApiPathsV1.USERS_ID).toRelativePath(), requestFields, responseFields)
            }
        }
        .body(requestPayload)
        .patch(ApiPathsV1.usersId(userId))
}

fun EndpointLargeTestBase.deleteUserApi(
    userId: UUID,
    responseFields: List<FieldDescriptor>? = null,
): Response {
    return request()
        .apply {
            if (responseFields != null) {
                this.withDocumentation(URI.create(ApiPathsV1.USERS_ID).toRelativePath(), null, responseFields)
            }
        }
        .delete(ApiPathsV1.usersId(userId))
}

fun EndpointLargeTestBase.loginUserApi(
    requestPayload: UserLoginRequest,
    responseFields: List<FieldDescriptor>? = null,
): Response {
    return request()
        .apply {
            if (responseFields != null) {
                this.withDocumentation(URI.create(ApiPathsV1.USER_LOGIN).toRelativePath(), null, responseFields)
            }
        }
        .body(requestPayload)
        .post(ApiPathsV1.USER_LOGIN)
}

fun EndpointLargeTestBase.createRandomUser(
    request: CreateUserRequest = CreateUserRequest.random()
): UserResponse {
    return createUserApi(request).expect2xx(UserResponse::class)
}

fun EndpointLargeTestBase.getPublicKeyApi(): String = request().get(ApiPaths.PUBLIC_KEY).path("body.result")
