package com.zemoga.app.di.modules

import android.app.Application
import android.content.Context
import com.app.core.interfaces.AppResources
import com.zemoga.app.data.ZemogaResources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseModule {
    @Binds
    abstract fun bindContext(zemogaApp: Application): Context

    @Binds
    abstract fun bindResources(zemogaResources: ZemogaResources): AppResources
}
