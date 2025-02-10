package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Seller(
  @Json(name = "id")
  val id: Long?,
  @Json(name = "nickname")
  val nickname: String?
)
