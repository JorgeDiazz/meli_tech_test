package com.meli.app.di.modules

import androidx.annotation.VisibleForTesting
import com.app.base.interfaces.Logger
import com.app.core.di.BasePath
import com.app.core.di.OkHttpClientBasic
import com.app.core.di.RetrofitMeli
import com.app.core.di.RetrofitNullSerializationEnabled
import com.app.core.network.ServerInterceptor
import com.meli.app.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okreplay.OkReplayInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val FLAVOR_TARGET_INTERNAL = "internal"
private const val EXTERNAL_REQUEST_TIMEOUT_IN_SECONDS = 60L
private const val INTERNAL_REQUEST_TIMEOUT_IN_SECONDS = 300L

@Module(includes = [InterceptorsModule::class])
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @VisibleForTesting
  val okReplayInterceptor = OkReplayInterceptor()

  @Provides
  @Singleton
  fun providesOkReplayInterceptor(): OkReplayInterceptor {
    return okReplayInterceptor
  }

  @Provides
  fun providesOkHttpClientBuilder(): OkHttpClient.Builder {
    val timeout =
      if (BuildConfig.FLAVOR_target == FLAVOR_TARGET_INTERNAL) INTERNAL_REQUEST_TIMEOUT_IN_SECONDS else EXTERNAL_REQUEST_TIMEOUT_IN_SECONDS
    return OkHttpClient.Builder()
      .connectTimeout(timeout, TimeUnit.SECONDS)
      .readTimeout(timeout, TimeUnit.SECONDS)
      .writeTimeout(timeout, TimeUnit.SECONDS)
  }

  @Provides
  @OkHttpClientBasic
  fun providesBasicOkHttpClient(
    builder: OkHttpClient.Builder,
  ): OkHttpClient {
    return builder.build()
  }

  @Provides
  @Singleton
  fun providesMoshi(): Moshi {
    return Moshi.Builder()
      .build()
  }

  @Provides
  @Singleton
  fun providesLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    val level = if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }
    logging.setLevel(level)
    return logging
  }

  @Provides
  @Singleton
  fun providesBaseOkHttpClient(
    builder: OkHttpClient.Builder,
    interceptor: Interceptor,
    okReplayInterceptor: OkReplayInterceptor,
    logger: Logger
  ): OkHttpClient {
    val serverInterceptor = ServerInterceptor(logger)
    return builder
      .addInterceptor(okReplayInterceptor)
      .addInterceptor(serverInterceptor)
      .addInterceptor(interceptor)
      .build()
  }

  @Provides
  @Singleton
  @RetrofitMeli
  @JvmSuppressWildcards
  fun providesOkHttpClient(
    builder: OkHttpClient.Builder,
    interceptor: Interceptor,
    interceptorList: Set<Interceptor>,
    okReplayInterceptor: OkReplayInterceptor,
  ): OkHttpClient {
    interceptorList.forEach {
      builder.addInterceptor(it)
    }
    return builder
      .addInterceptor(okReplayInterceptor)
      .addInterceptor(interceptor)
      .build()
  }

  @Provides
  @Singleton
  @BasePath
  fun providesBasePath(): String {
    return BuildConfig.BASE_URL
  }

  @Provides
  @Singleton
  @RetrofitNullSerializationEnabled
  fun providesRetrofitNullSerializationEnabled(
    @RetrofitMeli okHttpClient: OkHttpClient,
    @BasePath basePath: String
  ): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(basePath)
      .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
      .build()
  }
}
