package com.meli.items.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Address(
  @ColumnInfo(name = "state_id") val stateId: String,
  @ColumnInfo(name = "state_name") val stateName: String,
  @ColumnInfo(name = "city_name") val cityName: String
)
