package com.flab.hsw.core.jdbc

interface Identifiable<T> {
    val id: T

    val isIdentifiable: Boolean
}

interface LongIdentifiable : Identifiable<Long> {
    override val id: Long

    override val isIdentifiable: Boolean
        get() = id > UNIDENTIFIABLE

    companion object {
        const val UNIDENTIFIABLE: Long = -1L
    }
}
