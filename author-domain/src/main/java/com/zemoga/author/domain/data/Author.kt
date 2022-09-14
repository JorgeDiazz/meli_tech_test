package com.zemoga.author.domain.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zemoga.author.entities.AuthorResponse

@Entity(tableName = "author")
data class Author(
  @PrimaryKey @ColumnInfo(name = "id") val id: Int,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "username") val username: String,
  @ColumnInfo(name = "email") val email: String,
  @ColumnInfo(name = "address") val address: Address,
  @ColumnInfo(name = "phone") val phone: String,
  @ColumnInfo(name = "website") val website: String,
  @ColumnInfo(name = "company") val company: Company,
)

fun List<AuthorResponse>.toBaseModel(): List<Author> = map { it.toBaseModel() }
fun AuthorResponse.toBaseModel(): Author = Author(
  id, name, username, email,
  Address(
    address.street,
    address.suite,
    address.city,
    address.zipcode,
    Geolocation(address.geolocation.latitude, address.geolocation.longitude),
  ),
  phone,
  website,
  Company(company.name, company.catchPhrase, company.bs),
)
