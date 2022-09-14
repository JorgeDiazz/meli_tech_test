package com.zemoga.posts.di

import androidx.paging.ExperimentalPagingApi
import com.app.base.interfaces.FlowUseCase
import com.zemoga.posts.domain.usecases.DeleteNonFavoritePostsUseCase
import com.zemoga.posts.domain.usecases.DeletePostUseCase
import com.zemoga.posts.domain.usecases.UpdateFavoritePostUseCase
import com.zemoga.posts.qualifiers.DeleteNonFavoritePosts
import com.zemoga.posts.qualifiers.DeletePost
import com.zemoga.posts.qualifiers.UpdateFavoritePost
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
abstract class PostsBindsModule {
    @Binds
    @DeletePost
    @Singleton
    abstract fun bindsDeletePostUseCase(deletePostsUseCase: DeletePostUseCase): FlowUseCase<Int, Boolean>

    @Binds
    @DeleteNonFavoritePosts
    @Singleton
    abstract fun bindsDeleteNonFavoritePostsUseCase(deleteNonFavoritePostsUseCase: DeleteNonFavoritePostsUseCase): FlowUseCase<Unit, Boolean>

    @Binds
    @UpdateFavoritePost
    @Singleton
    abstract fun bindsUpdateFavoritePostUseCase(updateFavoritePostUseCase: UpdateFavoritePostUseCase): FlowUseCase<Pair<Int, Boolean>, Boolean>
}
