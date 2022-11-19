package com.flab.hsw.core.domain.user

import java.time.Instant

interface DateTimeAuditable {
    val createdAt: Instant

    fun changeTimeToNow(time: Instant = Instant.now())
}
