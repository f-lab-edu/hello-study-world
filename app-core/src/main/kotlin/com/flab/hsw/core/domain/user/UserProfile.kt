package com.flab.hsw.core.domain.user

import com.flab.hsw.core.domain.user.aggregate.UserProfileModel
import java.util.*

interface UserProfile {
    val id: UUID
    val nickname: String
    val email: String

    companion object {
        fun create(
            id: UUID,
            nickname: String,
            email: String
        ): UserProfile = UserProfileModel.create(
            id = id,
            nickname = nickname,
            email = email
        )
    }
}
