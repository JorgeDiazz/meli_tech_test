package com.meli.items.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meli.items.entities.ResultX

@Entity(tableName = "items")
data class Item(
  @PrimaryKey @ColumnInfo(name = "id") val id: Int,
  @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "thumbnail") val thumbnail: String,
  @ColumnInfo(name = "price") val price: Double,
  @ColumnInfo(name = "seller") val seller: Seller,
  @ColumnInfo(name = "address") val address: Address,
  @ColumnInfo(name = "attributes") val attributes: List<Attribute>
)

fun List<ResultX>.toBaseModel(): List<Item> = map { it.toBaseModel() }
fun ResultX.toBaseModel(): Item = Item(

  id = id?.substring(3)?.toInt() ?: 0,
  title = title ?: "",
  thumbnail = thumbnail ?: "",
  price = price ?: 0.0,
  seller = Seller(
    id = seller?.id ?: 0L,
    nickname = seller?.nickname ?: ""
  ),
  address = Address(
    stateId = address?.stateId ?: "",
    stateName = address?.stateName ?: "",
    cityName = address?.cityName ?: ""
  ),
  attributes = attributes?.map { attr ->
    Attribute(
      id = attr.id ?: "",
      name = attr.name ?: "",
      valueName = attr.valueName ?: "",
      valueId = attr.valueId
    )
  } ?: emptyList()
)
