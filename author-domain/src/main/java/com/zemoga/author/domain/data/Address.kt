package com.zemoga.author.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Address(
  @ColumnInfo(name = "street") val street: String,
  @ColumnInfo(name = "suite") val suite: String,
  @ColumnInfo(name = "city") val city: String,
  @ColumnInfo(name = "zipcode") val zipcode: String,
  @ColumnInfo(name = "geolocation") val geolocation: Geolocation,
)
