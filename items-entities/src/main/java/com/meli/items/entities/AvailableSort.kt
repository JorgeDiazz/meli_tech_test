package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvailableSort(
  @Json(name = "id")
  val id: String?,
  @Json(name = "name")
  val name: String?
)
