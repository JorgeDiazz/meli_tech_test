package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class X174333663978(
  @Json(name = "attribute_combinations")
  val attributeCombinations: List<AttributeCombination>?,
  @Json(name = "attributes")
  val attributes: List<AttributeX>?,
  @Json(name = "name")
  val name: String?,
  @Json(name = "pictures_qty")
  val picturesQty: Double?,
  @Json(name = "price")
  val price: Double?,
  @Json(name = "ratio")
  val ratio: String?,
  @Json(name = "thumbnail")
  val thumbnail: String?,
  @Json(name = "user_product_id")
  val userProductId: String?
)
