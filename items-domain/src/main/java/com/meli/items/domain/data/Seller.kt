package com.meli.items.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Seller(
  @ColumnInfo(name = "id") val id: Long,
  @ColumnInfo(name = "nickname") val nickname: String
)
