package com.flab.hsw.core.domain

import java.time.Instant

interface RecordedTimeMixin {
    val registeredAt: Instant
    val lastUpdateAt: Instant
}
