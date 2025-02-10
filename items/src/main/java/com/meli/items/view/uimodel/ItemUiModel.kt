package com.meli.items.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemUiModel(
  val id: Int,
  val title: String,
  val thumbnail: String,
  val price: String,
  val seller: SellerUiModel,
  val attributes: List<AttributeUiModel>
) : Parcelable
