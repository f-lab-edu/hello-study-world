package testcase.medium

import java.util.*

object MediumTestDataDeclarations {
    private val uuidsOfUserEntityData = listOf(
        UUID.fromString("e8d1b7dd-4ca7-47ac-903f-af585c13c041"),
    )

    fun randomUuidFromUserEntityData(): UUID = uuidsOfUserEntityData.random()
}
