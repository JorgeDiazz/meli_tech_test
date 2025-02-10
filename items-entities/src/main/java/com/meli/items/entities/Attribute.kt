package com.meli.items.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attribute(
    @Json(name = "attribute_group_id")
    val attributeGroupId: String?,
    @Json(name = "attribute_group_name")
    val attributeGroupName: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "source")
    val source: Long?,
    @Json(name = "value_id")
    val valueId: String?,
    @Json(name = "value_name")
    val valueName: String?,
    @Json(name = "value_struct")
    val valueStruct: ValueStruct?,
    @Json(name = "value_type")
    val valueType: String?,
    @Json(name = "values")
    val values: List<ValueXX>?
)
