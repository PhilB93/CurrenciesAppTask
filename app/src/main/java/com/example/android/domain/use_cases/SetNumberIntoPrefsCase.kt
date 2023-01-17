package com.example.android.domain.use_cases

import com.example.android.domain.repository.CurrencyRepository
import javax.inject.Inject

class SetNumberIntoPrefsCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(number: Double) {
        return repository.setNumber(number)
    }
}
