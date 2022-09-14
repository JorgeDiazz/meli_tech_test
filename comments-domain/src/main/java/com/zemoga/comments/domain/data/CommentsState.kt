package com.zemoga.comments.domain.data

sealed class CommentsState {
  data class GettingCommentsSuccessfully(val commentsList: List<Comment>) : CommentsState()
  object ErrorGettingComments : CommentsState()
}
