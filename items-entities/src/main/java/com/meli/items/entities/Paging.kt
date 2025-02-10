package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Paging(
    @Json(name = "limit")
    val limit: Double?,
    @Json(name = "offset")
    val offset: Double?,
    @Json(name = "primary_results")
    val primaryResults: Double?,
    @Json(name = "total")
    val total: Double?
)
