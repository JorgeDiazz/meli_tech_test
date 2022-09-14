package com.zemoga.author.domain.room.converters

import androidx.room.TypeConverter
import com.zemoga.author.domain.data.Address
import com.zemoga.author.domain.data.Company
import com.zemoga.author.domain.data.Geolocation
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Represents the converters used by Room in order to serialize abstract entities.
 *
 */
@ExperimentalSerializationApi
class RoomConverters {

  @TypeConverter
  fun fromAddress(value: Address) = Json.encodeToString(value)

  @TypeConverter
  fun toAddressEntity(value: String): Address = Json.decodeFromString(value)

  @TypeConverter
  fun fromCompany(value: Company) = Json.encodeToString(value)

  @TypeConverter
  fun toCompanyEntity(value: String): Company = Json.decodeFromString(value)

  @TypeConverter
  fun fromGeolocation(value: Geolocation) = Json.encodeToString(value)

  @TypeConverter
  fun toGeolocationEntity(value: String): Geolocation = Json.decodeFromString(value)
}
