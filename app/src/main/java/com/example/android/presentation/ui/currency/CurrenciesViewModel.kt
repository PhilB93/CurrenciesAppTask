package com.example.android.presentation.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.domain.model.Currency
import com.example.android.domain.use_cases.GetCurrencyFromApiUseCase
import com.example.android.domain.use_cases.GetNumberFromPrefsCase
import com.example.android.domain.utill.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIME_UPDATES = 5000L

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getCurrency: GetCurrencyFromApiUseCase,
    private val getNumber: GetNumberFromPrefsCase
) : ViewModel() {

    private var updateJob: Job? = null
    private var getNumberJob: Job? = null

    private val selectedCurrency = MutableStateFlow<Currency?>(null)

    private val number = MutableStateFlow(1.0)

    private var _response = MutableStateFlow<Record<List<Currency>>>(Record.Empty)
    val response = combine(_response, selectedCurrency, number) { response, sCurrency, num ->
        when (response) {
            is Record.Success -> Record.Success(
                sCurrency?.let { sC ->
                    val actualList = response.data
                        .filter { it.img != 0 } // filtering currencies without image
                        .map { cr ->
                            cr.apply {
                                this.rate *= num
                            }
                        }
                        .toMutableList()
                    actualList.removeIf { it.name == sC.name }
                    actualList.add(0, sC.apply {
                        this.rate = num
                        this.isSelected = true
                    })
                    actualList.toList()
                } ?: response.data.filter { it.img != 0 } // filtering currencies without image
            )
            else -> response
        }
    }

    init {
        getCurrency()
        getNumber()
    }

    private fun getNumber() {
        getNumberJob?.cancel()
        getNumberJob =
            viewModelScope.launch { getNumber.invoke().collect { number.tryEmit(it) } }
    }

    private fun getCurrency() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            while (true) {
                setResponse()
                delay(TIME_UPDATES)
            }
        }
    }

    private suspend fun setResponse() {
        getCurrency.invoke().onEach { _response.tryEmit(it) }.collect()
    }

    fun setCounter(currency: Currency) {
        selectedCurrency.tryEmit(currency)
    }
}
