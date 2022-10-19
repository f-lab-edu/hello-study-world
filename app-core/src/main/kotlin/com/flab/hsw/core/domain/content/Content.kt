package com.flab.hsw.core.domain.content

import com.flab.hsw.core.domain.SoftDeletable
import com.flab.hsw.core.domain.user.User
import java.time.Instant
import java.util.*

interface Content : SoftDeletable {
    val id: UUID
    val url: String
    val description: String
    val provider: User
    val registeredAt: Instant
    val lastUpdateAt: Instant

    companion object {
        const val LENGTH_DESCRIPTION_MIN = 1
        const val LENGTH_DESCRIPTION_MAX = 200
    }
}
