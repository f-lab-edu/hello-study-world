/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.endpoint.v1

/**
 * @since 2021-08-10
 */
object ApiPathsV1 {
    const val V1 = "/v1"

    const val USERS = "$V1/users"
    const val USERS_ID = "$USERS/${ApiVariableV1.PATH_ID}"

    const val CONTENT = "$V1/content"

    const val RECOMMEND = "recommend"
    const val CONTENT_RECOMMENDATION = "${CONTENT}/${RECOMMEND}"

    const val USER_LOGIN = "${USERS}/login"

}
