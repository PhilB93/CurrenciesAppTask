package com.example.android.domain.repository

import com.example.android.domain.model.Currency
import com.example.android.domain.utill.Record
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getAllCurrencyFromApi(): Flow<Record<List<Currency>>>
    fun getNumber(): Flow<Double>
    suspend fun setNumber(number: Double)
}