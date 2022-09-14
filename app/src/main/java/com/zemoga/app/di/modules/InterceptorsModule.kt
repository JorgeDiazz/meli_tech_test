package com.zemoga.app.di.modules

import com.app.core.network.ServerInterceptor
import com.app.core.network.NetworkConnectivityInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
abstract class InterceptorsModule {

  @Binds
  @IntoSet
  abstract fun bindsServerInterceptor(serverInterceptor: ServerInterceptor): Interceptor

  @Binds
  @IntoSet
  abstract fun bindsNetworkConnectivityInterceptor(networkConnectivityInterceptor: NetworkConnectivityInterceptor): Interceptor
}
