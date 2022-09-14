package com.zemoga.comments.view.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentUiModel(
  val postId: Int,
  val id: Int,
  val name: String,
  val email: String,
  val body: String,
) : Parcelable
