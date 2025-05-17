package com.simple.lplcodingchallenge.data.di

import com.simple.lplcodingchallenge.domain.repository.PostRepository
import com.simple.lplcodingchallenge.domain.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Singleton
    @Binds
    abstract fun providePostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository
}