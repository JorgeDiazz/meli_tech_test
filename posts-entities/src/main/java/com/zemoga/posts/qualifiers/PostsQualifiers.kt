package com.zemoga.posts.qualifiers

import javax.inject.Qualifier

@Qualifier
annotation class GetPosts

@Qualifier
annotation class DeletePost

@Qualifier
annotation class DeleteNonFavoritePosts

@Qualifier
annotation class UpdateFavoritePost