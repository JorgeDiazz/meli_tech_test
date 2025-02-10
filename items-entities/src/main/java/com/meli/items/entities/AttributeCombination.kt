package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AttributeCombination(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "value_id")
    val valueId: Any?,
    @Json(name = "value_name")
    val valueName: String?,
    @Json(name = "value_struct")
    val valueStruct: Any?,
    @Json(name = "values")
    val values: Any?
)
