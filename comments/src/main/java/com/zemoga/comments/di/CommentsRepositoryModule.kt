package com.zemoga.comments.di

import android.content.Context
import androidx.room.Room
import com.app.base.interfaces.Logger
import com.app.core.di.RetrofitNullSerializationEnabled
import com.zemoga.comments.domain.data.CommentsDatabase
import com.zemoga.comments.domain.repositories.CommentsRepository
import com.zemoga.comments.domain.repositories.ICommentsRepository
import com.zemoga.comments.domain.services.CommentsRemoteDataSource
import com.zemoga.comments.domain.services.CommentsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalSerializationApi
object CommentsRepositoryModule {
  @Provides
  @Singleton
  fun providesCommentsService(
    @RetrofitNullSerializationEnabled retrofit: Retrofit
  ): CommentsService {
    return retrofit.create(CommentsService::class.java)
  }

  @Provides
  @Singleton
  fun providesCommentsRepository(
    context: Context,
    logger: Logger,
    commentsRemoteDataSource: CommentsRemoteDataSource,
  ): ICommentsRepository {
    val database =
      Room.databaseBuilder(context, CommentsDatabase::class.java, "comments-database")
        .fallbackToDestructiveMigration()
        .build()

    return CommentsRepository(logger, commentsRemoteDataSource, database)
  }
}
