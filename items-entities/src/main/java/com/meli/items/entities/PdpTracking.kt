package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PdpTracking(
  @Json(name = "group")
  val group: Boolean?,
  @Json(name = "product_info")
  val productInfo: List<ProductInfo?>?
)
