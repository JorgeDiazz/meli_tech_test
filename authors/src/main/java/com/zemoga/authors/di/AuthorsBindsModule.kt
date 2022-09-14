package com.zemoga.authors.di

import androidx.paging.ExperimentalPagingApi
import com.app.base.interfaces.FlowUseCase
import com.zemoga.author.domain.data.AuthorsState
import com.zemoga.author.domain.usecases.GetAuthorsUseCase
import com.zemoga.author.qualifiers.GetAuthors
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
abstract class AuthorsBindsModule {
  @Binds
  @GetAuthors
  @Singleton
  abstract fun bindsGetAuthorsUseCase(getAuthorsUseCase: GetAuthorsUseCase): FlowUseCase<Unit, AuthorsState>
}
