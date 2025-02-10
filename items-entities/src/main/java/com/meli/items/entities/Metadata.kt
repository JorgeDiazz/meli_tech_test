package com.meli.items.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Metadata(
  @Json(name = "additional_bank_interest")
  val additionalBankInterest: Boolean?,
  @Json(name = "meliplus_installments")
  val meliplusInstallments: Boolean?
)
