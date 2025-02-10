package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvailableFilter(
  @Json(name = "id")
  val id: String?,
  @Json(name = "name")
  val name: String?,
  @Json(name = "type")
  val type: String?,
  @Json(name = "values")
  val values: List<Value?>?
)
