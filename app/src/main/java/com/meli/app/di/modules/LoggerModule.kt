package com.meli.app.di.modules

import com.app.base.interfaces.Logger
import com.meli.app.MeliDebugTree
import com.meli.app.MeliLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
  @Provides
  @Singleton
  fun providesLoggerImplementation(): Logger {
    val tree = MeliDebugTree() // The logger could be changed according to current environment
    return MeliLogger(tree)
  }
}
