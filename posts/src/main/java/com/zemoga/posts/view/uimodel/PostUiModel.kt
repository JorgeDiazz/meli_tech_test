package com.zemoga.posts.view.uimodel

import android.os.Parcelable
import com.zemoga.authors.view.uimodel.AuthorUiModel
import com.zemoga.comments.view.uimodel.CommentUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostUiModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    var menuVisible: Boolean,
    var favorite: Boolean,
    var deleted: Boolean,
    val authorUiModel: AuthorUiModel,
    val commentsUiModel: List<CommentUiModel>
) : Parcelable
