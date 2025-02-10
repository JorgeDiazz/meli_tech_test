package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
  @Json(name = "city_id")
  val cityId: String?,
  @Json(name = "city_name")
  val cityName: String?,
  @Json(name = "state_id")
  val stateId: String?,
  @Json(name = "state_name")
  val stateName: String?
)
