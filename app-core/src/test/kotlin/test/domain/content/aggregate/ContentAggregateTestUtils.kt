package test.domain.content.aggregate

import com.flab.hsw.core.domain.content.Content
import com.flab.hsw.core.domain.content.aggregate.ContentModel
import com.flab.hsw.core.domain.user.User
import com.github.javafaker.Faker
import test.domain.user.aggregate.randomUser

fun randomContent(
    url: String = Faker().internet().url(),
    description: String = Faker().lorem().word(),
    provider: User = randomUser()
) : Content = ContentModel.create(
    url = url ,
    description = description,
    provider = provider
)
