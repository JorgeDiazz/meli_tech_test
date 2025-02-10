package com.meli.app.di.modules

import android.app.Application
import android.content.Context
import com.app.core.interfaces.AppResources
import com.meli.app.data.MeliResources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BaseModule {
    @Binds
    abstract fun bindContext(meliApp: Application): Context

    @Binds
    abstract fun bindResources(meliResources: MeliResources): AppResources
}
