package com.meli.items.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttributeUiModel(
    val name: String,
    val valueName: String
) : Parcelable
