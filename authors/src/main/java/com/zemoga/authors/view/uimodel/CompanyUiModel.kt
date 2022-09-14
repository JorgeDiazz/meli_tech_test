package com.zemoga.authors.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyUiModel(
  val name: String,
  val catchPhrase: String,
  val bs: String,
) : Parcelable
