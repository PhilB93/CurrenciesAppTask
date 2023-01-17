package com.example.android.domain.use_cases

import com.example.android.domain.model.Currency
import com.example.android.domain.repository.CurrencyRepository
import com.example.android.domain.utill.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyFromApiUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
     suspend operator fun invoke(): Flow<Record<List<Currency>>> {
         return repository.getAllCurrencyFromApi()
     }
}
