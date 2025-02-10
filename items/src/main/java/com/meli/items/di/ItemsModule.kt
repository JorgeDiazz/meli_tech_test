package com.meli.items.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.app.base.interfaces.FlowUseCase
import com.meli.items.domain.data.Item
import com.meli.items.domain.repositories.IItemsRepository
import com.meli.items.domain.usecases.SearchItemsUseCase
import com.meli.items.qualifiers.GetItems
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [ItemsRepositoryModule::class])
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
object ItemsModule {

  @Provides
  @GetItems
  @Singleton
  fun providesGetItemsUseCase(itemsRepository: IItemsRepository): FlowUseCase<String, PagingData<Item>> {
    return SearchItemsUseCase(itemsRepository)
  }

}
