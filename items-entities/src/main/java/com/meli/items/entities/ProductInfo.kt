package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductInfo(
    @Json(name = "id")
    val id: String?,
    @Json(name = "score")
    val score: Double?,
    @Json(name = "status")
    val status: String?
)
