package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Conditions(
    @Json(name = "context_restrictions")
    val contextRestrictions: List<String?>?,
    @Json(name = "eligible")
    val eligible: Boolean?,
    @Json(name = "end_time")
    val endTime: Any?,
    @Json(name = "start_time")
    val startTime: Any?
)
