package com.example.android.di

import com.example.android.data.mappers.MapCurrency
import com.example.android.data.mappers.MapCurrencyPojoToCurrency
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RouteMapperModule {

    @Provides
    @Singleton
    fun provideMapRoute(
        currencyPojoToCurrency: MapCurrencyPojoToCurrency,
    ): MapCurrency {
        return MapCurrency(
            currencyPojoToCurrency
        )
    }

    @Provides
    fun provideMapCurrencyPojoToCurrency(): MapCurrencyPojoToCurrency {
        return MapCurrencyPojoToCurrency()
    }

}
