package com.meli.app.di.components

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.app.core.CoreComponent
import com.meli.app.di.modules.ActivityAggregatorModule
import com.meli.app.di.modules.BaseModule
import com.meli.app.di.modules.CacheModule
import com.meli.app.di.modules.FragmentModule
import com.meli.app.di.modules.LoggerModule
import com.meli.app.di.modules.MeliAppModule
import com.meli.app.di.modules.NetworkModule
import com.meli.items.di.ItemsModule
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * Represents the modules provided to the app.
 *
 */
@InstallIn(SingletonComponent::class)
@EntryPoint
@ExperimentalPagingApi
@ExperimentalSerializationApi
@Component(
  modules = [
    BaseModule::class,
    NetworkModule::class,
    ActivityAggregatorModule::class,
    CacheModule::class,
    FragmentModule::class,
    LoggerModule::class,
    MeliAppModule::class,
    ItemsModule::class,
  ]
)
interface AppComponent : CoreComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }
}
