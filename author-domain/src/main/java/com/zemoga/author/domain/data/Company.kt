package com.zemoga.author.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Company(
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "catchPhrase") val catchPhrase: String,
  @ColumnInfo(name = "bs") val bs: String,
)
