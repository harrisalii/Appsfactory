package com.appsfactory.test.di

import com.appsfactory.test.data.repository.LastFMRepositoryImpl
import com.appsfactory.test.domain.repository.LastFMRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLastFMRepository(
        lastFMRepositoryImpl: LastFMRepositoryImpl
    ): LastFMRepository
}