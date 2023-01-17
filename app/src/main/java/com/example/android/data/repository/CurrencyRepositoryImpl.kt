package com.example.android.data.repository

import com.example.android.data.data_source.api.CurrencyApiService
import com.example.android.data.data_source.api.helper.CurrencyRemoteException
import com.example.android.data.data_source.pref.PreferenceManager
import com.example.android.data.mappers.ErrorMapper
import com.example.android.data.mappers.MapCurrency
import com.example.android.data.utill.ResourceManager
import com.example.android.domain.model.Currency
import com.example.android.domain.repository.CurrencyRepository
import com.example.android.domain.utill.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepositoryImpl(
    private val api: CurrencyApiService,
    private val mapError: ErrorMapper,
    private val resourceManager: ResourceManager,
    private val mapCurrency: MapCurrency,
    private val preferenceManager: PreferenceManager
) : CurrencyRepository {
    override suspend fun getAllCurrencyFromApi(): Flow<Record<List<Currency>>> = flow {
        emit(Record.Loading)
        try {
            api.getAllCurrency()
                .run {
                    emit(Record.Success(mapCurrency
                        .currencyPojoToCurrency
                        .mapList(this)
                        .map {
                            it.apply {
                                name = resourceManager.getStringResourceId(this.base)
                                img = resourceManager.getDrawableId(this.base)
                            }
                        }
                    ))
                }
        } catch (e: CurrencyRemoteException) {
            emit(mapError.mapErrorRecord(e))
        }
    }

    override fun getNumber(): Flow<Double> {
        return preferenceManager.getNumber
    }

    override suspend fun setNumber(number: Double) {
        preferenceManager.setNumber(number)
    }
}