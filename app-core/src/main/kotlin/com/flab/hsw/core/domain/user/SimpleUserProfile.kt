package com.flab.hsw.core.domain.user

import com.flab.hsw.core.domain.user.aggregate.SimpleUserProfileModel
import java.util.*

interface SimpleUserProfile {
    val id: UUID
    val nickname: String
    val email: String

    companion object {
        fun create(
            id: UUID,
            nickname: String,
            email: String
        ): SimpleUserProfile = SimpleUserProfileModel(
            id = id,
            nickname = nickname,
            email = email
        )
    }
}
