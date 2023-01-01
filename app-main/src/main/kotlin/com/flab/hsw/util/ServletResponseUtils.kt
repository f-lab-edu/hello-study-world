/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.util

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.addCookieWithHttpOnly(keyValue: Pair<String, String>) {
    val cookie = Cookie(keyValue.first, keyValue.second)
    cookie.isHttpOnly = true
    addCookie(cookie)
}
