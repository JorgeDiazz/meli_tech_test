package com.zemoga.authors.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressUiModel(
  val street: String,
  val suite: String,
  val city: String,
  val zipcode: String,
  val geolocation: GeolocationUiModel,
) : Parcelable
