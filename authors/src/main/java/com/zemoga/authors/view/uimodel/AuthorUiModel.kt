package com.zemoga.authors.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorUiModel(
  val id: Int,
  val name: String,
  val username: String,
  val email: String,
  val address: AddressUiModel,
  val phone: String,
  val website: String,
  val company: CompanyUiModel,
) : Parcelable
