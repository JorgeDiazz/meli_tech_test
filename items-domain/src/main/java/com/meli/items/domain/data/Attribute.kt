package com.meli.items.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Attribute(
  @ColumnInfo(name = "id") val id: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "value_name") val valueName: String,
  @ColumnInfo(name = "value_id") val valueId: String?
)
