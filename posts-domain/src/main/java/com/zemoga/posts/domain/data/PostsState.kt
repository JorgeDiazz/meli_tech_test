package com.zemoga.posts.domain.data

sealed class PostsState {
    data class PostUpdatedSuccessfully(val postId: Int) : PostsState()
    object ErrorUpdatingPost : PostsState()

    data class PostDeletedSuccessfully(val postId: Int) : PostsState()
    object ErrorDeletingPost : PostsState()

    object NonFavoritePostsDeletedSuccessfully : PostsState()
    object ErrorDeletingNonFavoritePosts : PostsState()
}
