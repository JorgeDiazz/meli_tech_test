package com.zemoga.author.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyResponse(
  @Json(name = "name")
  val name: String,
  @Json(name = "catchPhrase")
  val catchPhrase: String,
  @Json(name = "bs")
  val bs: String,
)
