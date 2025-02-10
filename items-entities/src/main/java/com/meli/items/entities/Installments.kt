package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Installments(
  @Json(name = "amount")
  val amount: Double?,
  @Json(name = "currency_id")
  val currencyId: String?,
  @Json(name = "metadata")
  val metadata: Metadata?,
  @Json(name = "quantity")
  val quantity: Double?,
  @Json(name = "rate")
  val rate: Double?
)
