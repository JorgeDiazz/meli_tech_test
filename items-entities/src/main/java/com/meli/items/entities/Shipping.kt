package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Shipping(
  @Json(name = "benefits")
  val benefits: Any?,
  @Json(name = "free_shipping")
  val freeShipping: Boolean?,
  @Json(name = "logistic_type")
  val logisticType: String?,
  @Json(name = "mode")
  val mode: String?,
  @Json(name = "promise")
  val promise: Any?,
  @Json(name = "shipping_score")
  val shippingScore: Double?,
  @Json(name = "store_pick_up")
  val storePickUp: Boolean?,
  @Json(name = "tags")
  val tags: List<String?>?
)
