package com.zemoga.posts.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.app.base.interfaces.Cache
import com.app.base.interfaces.Logger
import com.app.core.di.RetrofitNullSerializationEnabled
import com.zemoga.posts.domain.data.PostsDatabase
import com.zemoga.posts.domain.repositories.IPostsRepository
import com.zemoga.posts.domain.repositories.PostsRepository
import com.zemoga.posts.domain.services.PostsRemoteDataSource
import com.zemoga.posts.domain.services.PostsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsRepositoryModule {
    @Provides
    @Singleton
    fun providesPostsService(
        @RetrofitNullSerializationEnabled retrofit: Retrofit
    ): PostsService {
        return retrofit.create(PostsService::class.java)
    }

    @Provides
    @Singleton
    @ExperimentalPagingApi
    fun providesPostsRepository(
        context: Context,
        logger: Logger,
        cache: Cache,
        postsRemoteDataSource: PostsRemoteDataSource,
    ): IPostsRepository {
        val database =
            Room.databaseBuilder(context, PostsDatabase::class.java, "posts-database")
                .fallbackToDestructiveMigration()
                .build()

        return PostsRepository(logger, cache, postsRemoteDataSource, database)
    }
}
