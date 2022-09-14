package com.zemoga.author.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Geolocation(
  @ColumnInfo(name = "latitude") val latitude: String,
  @ColumnInfo(name = "longitude") val longitude: String,
)
