package com.zemoga.comments.di

import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi

@Module(includes = [CommentsRepositoryModule::class, CommentsBindsModule::class])
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
@ExperimentalSerializationApi
object CommentsModule
