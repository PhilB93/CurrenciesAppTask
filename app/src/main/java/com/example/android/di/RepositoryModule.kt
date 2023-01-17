package com.example.android.di

import android.content.Context
import com.example.android.data.data_source.api.CurrencyApiService
import com.example.android.data.data_source.pref.PreferenceManager
import com.example.android.data.mappers.ErrorMapper
import com.example.android.data.mappers.MapCurrency
import com.example.android.data.repository.CurrencyRepositoryImpl
import com.example.android.data.utill.ResourceManager
import com.example.android.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideResourceManager(
        @ApplicationContext context: Context
    ): ResourceManager {
        return ResourceManager(context)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        api: CurrencyApiService,
        mapError: ErrorMapper,
        resourceManager: ResourceManager,
        mapCurrency: MapCurrency,
        preferenceManager: PreferenceManager
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            api = api,
            mapError = mapError,
            resourceManager = resourceManager,
            mapCurrency = mapCurrency,
            preferenceManager = preferenceManager
        )
    }
}
