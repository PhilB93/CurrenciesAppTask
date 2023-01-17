package com.example.android.domain.use_cases

import com.example.android.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNumberFromPrefsCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<Double> {
        return repository.getNumber()
    }
}
