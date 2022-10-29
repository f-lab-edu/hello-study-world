package com.flab.hsw.core.domain.user.aggregate

import com.flab.hsw.core.domain.user.SimpleUserProfile
import java.util.*

internal class SimpleUserProfileModel(
    override val id: UUID,
    override val nickname: String,
    override val email: String
) : SimpleUserProfile
