package com.zemoga.author.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeolocationResponse(
  @Json(name = "lat")
  val latitude: String,
  @Json(name = "lng")
  val longitude: String,
)
