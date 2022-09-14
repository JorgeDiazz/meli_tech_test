package com.zemoga.authors.di

import android.content.Context
import androidx.room.Room
import com.app.base.interfaces.Logger
import com.app.core.di.RetrofitNullSerializationEnabled
import com.zemoga.author.domain.data.AuthorsDatabase
import com.zemoga.author.domain.repositories.AuthorsRepository
import com.zemoga.author.domain.repositories.IAuthorsRepository
import com.zemoga.author.domain.services.AuthorsRemoteDataSource
import com.zemoga.author.domain.services.AuthorsService
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
object AuthorsRepositoryModule {
  @Provides
  @Singleton
  fun providesAuthorsService(
    @RetrofitNullSerializationEnabled retrofit: Retrofit
  ): AuthorsService {
    return retrofit.create(AuthorsService::class.java)
  }

  @Provides
  @Singleton
  fun providesAuthorsRepository(
    context: Context,
    logger: Logger,
    authorsRemoteDataSource: AuthorsRemoteDataSource,
  ): IAuthorsRepository {
    val database =
      Room.databaseBuilder(context, AuthorsDatabase::class.java, "authors-database")
        .fallbackToDestructiveMigration()
        .build()

    return AuthorsRepository(logger, authorsRemoteDataSource, database)
  }
}
