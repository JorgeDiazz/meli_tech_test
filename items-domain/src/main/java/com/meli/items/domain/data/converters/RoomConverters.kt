package com.meli.items.domain.data.converters

import androidx.room.TypeConverter
import com.meli.items.domain.data.Address
import com.meli.items.domain.data.Attribute
import com.meli.items.domain.data.Seller
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

  private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
  }

  @TypeConverter
  fun fromAttribute(value: Attribute) = Json.encodeToString(value)

  @TypeConverter
  fun toAttributeEntity(value: String): Attribute = Json.decodeFromString(value)

  @TypeConverter
  fun fromAttributesList(value: List<Attribute>): String {
    return json.encodeToString(value)
  }

  @TypeConverter
  fun toAttributesList(value: String): List<Attribute> {
    return try {
      json.decodeFromString(value)
    } catch (e: Exception) {
      emptyList()
    }
  }

  @TypeConverter
  fun fromSeller(value: Seller): String {
    return json.encodeToString(value)
  }

  @TypeConverter
  fun toSeller(value: String): Seller {
    return try {
      json.decodeFromString(value)
    } catch (e: Exception) {
      Seller(0L, "") // Default empty seller
    }
  }

  @TypeConverter
  fun fromAddress(value: Address): String {
    return json.encodeToString(value)
  }

  @TypeConverter
  fun toAddress(value: String): Address {
    return try {
      json.decodeFromString(value)
    } catch (e: Exception) {
      Address("", "", "") // Default empty address
    }
  }
}
