package com.meli.items.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.app.base.interfaces.Cache
import com.app.base.interfaces.Logger
import com.app.core.di.RetrofitNullSerializationEnabled
import com.meli.items.domain.data.ItemsDatabase
import com.meli.items.domain.repositories.IItemsRepository
import com.meli.items.domain.repositories.ItemsRepository
import com.meli.items.domain.services.ItemsRemoteDataSource
import com.meli.items.domain.services.ItemsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItemsRepositoryModule {
    @Provides
    @Singleton
    fun providesItemsService(
        @RetrofitNullSerializationEnabled retrofit: Retrofit
    ): ItemsService {
        return retrofit.create(ItemsService::class.java)
    }

    @Provides
    @Singleton
    @ExperimentalPagingApi
    fun providesItemsRepository(
      context: Context,
      logger: Logger,
      cache: Cache,
      itemsRemoteDataSource: ItemsRemoteDataSource,
    ): IItemsRepository {
        val database =
            Room.databaseBuilder(context, ItemsDatabase::class.java, "items-database")
                .fallbackToDestructiveMigration()
                .build()

        return ItemsRepository(logger, cache, itemsRemoteDataSource, database)
    }
}
