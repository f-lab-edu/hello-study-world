package com.flab.hsw.core.domain.content.command.aggregate

import com.flab.hsw.core.domain.content.command.CreateContentCommand
import java.net.URLDecoder
import java.util.*

internal class CreateContentCommandModel(
    override val id: UUID,
    url: String,
    override val description: String,
    override val providerUserId: UUID,
) : CreateContentCommand {
    override val url: String = URLDecoder.decode(url, Charsets.UTF_8)
}
