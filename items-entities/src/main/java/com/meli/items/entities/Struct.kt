package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Struct(
  @Json(name = "number")
  val number: Double?,
  @Json(name = "unit")
  val unit: String?
)
