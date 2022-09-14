package com.zemoga.authors.di

import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi

@Module(includes = [AuthorsRepositoryModule::class, AuthorsBindsModule::class])
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
@ExperimentalSerializationApi
object AuthorsModule
