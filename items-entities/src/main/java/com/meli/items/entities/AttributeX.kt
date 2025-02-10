package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttributeX(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "value_name")
    val valueName: String?,
    @Json(name = "value_type")
    val valueType: String?
)
