package com.zemoga.comments.di

import androidx.paging.ExperimentalPagingApi
import com.app.base.interfaces.FlowUseCase
import com.zemoga.comments.domain.data.CommentsState
import com.zemoga.comments.domain.usecases.GetCommentsUseCase
import com.zemoga.comments.qualifiers.GetComments
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
abstract class CommentsBindsModule {
  @Binds
  @GetComments
  @Singleton
  abstract fun bindsGetCommentsUseCase(getCommentsUseCase: GetCommentsUseCase): FlowUseCase<Unit, CommentsState>
}
