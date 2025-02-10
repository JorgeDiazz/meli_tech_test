package com.meli.items.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerUiModel(
  val nickname: String,
  val address: String
) : Parcelable
