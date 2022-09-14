package com.zemoga.authors.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeolocationUiModel(
  val latitude: String,
  val longitude: String,
) : Parcelable
