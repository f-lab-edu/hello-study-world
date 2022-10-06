/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.domain

/**
 * @since 2021-08-10
 */
interface SoftDeletable {
    val deleted: Boolean

    fun delete(): SoftDeletable
}
