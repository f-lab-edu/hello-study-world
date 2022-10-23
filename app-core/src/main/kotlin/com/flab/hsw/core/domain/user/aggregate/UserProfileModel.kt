package com.flab.hsw.core.domain.user.aggregate

import com.flab.hsw.core.domain.user.UserProfile
import java.util.*

internal class UserProfileModel(
    override val id: UUID,
    override val nickname: String,
    override val email: String
) : UserProfile {
    companion object {
        fun create(
            id: UUID,
            nickname: String,
            email: String
        ): UserProfile = UserProfileModel(
            id = id,
            nickname = nickname,
            email = email
        )
    }
}
