package com.zemoga.author.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResponse(
  @Json(name = "street")
  val street: String,
  @Json(name = "suite")
  val suite: String,
  @Json(name = "city")
  val city: String,
  @Json(name = "zipcode")
  val zipcode: String,
  @Json(name = "geo")
  val geolocation: GeolocationResponse,
)
