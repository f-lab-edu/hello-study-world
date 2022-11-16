package com.flab.hsw.endpoint.v1.content.recommend

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.*
import javax.validation.constraints.NotEmpty

@JsonDeserialize
data class CreateContentRecommendationRequest(

    @field:NotEmpty
    @JsonProperty
    val contentId: Long
)
