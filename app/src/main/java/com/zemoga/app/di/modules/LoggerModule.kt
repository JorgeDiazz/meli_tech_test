package com.zemoga.app.di.modules

import com.app.base.interfaces.Logger
import com.zemoga.app.ZemogaDebugTree
import com.zemoga.app.ZemogaLogger
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
    val tree = ZemogaDebugTree() // The logger could be changed according to current environment
    return ZemogaLogger(tree)
  }
}
