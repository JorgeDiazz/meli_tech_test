package com.zemoga.posts.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.app.base.interfaces.FlowUseCase
import com.zemoga.posts.domain.data.Post
import com.zemoga.posts.domain.repositories.IPostsRepository
import com.zemoga.posts.domain.usecases.GetPostsUseCase
import com.zemoga.posts.qualifiers.GetPosts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [PostsRepositoryModule::class, PostsBindsModule::class])
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
object PostsModule {

    @Provides
    @GetPosts
    @Singleton
    fun providesGetPostsUseCase(postsRepository: IPostsRepository): FlowUseCase<Unit, PagingData<Post>> {
        return GetPostsUseCase(postsRepository)
    }

}
