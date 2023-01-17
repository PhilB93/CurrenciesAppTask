package com.example.android.data.mappers

import com.example.android.data.data_source.api.pojo.CurrencyPojo
import com.example.android.domain.model.Currency
import com.example.android.domain.utill.Mapper

data class MapCurrency(
    val currencyPojoToCurrency: MapCurrencyPojoToCurrency,
)

class MapCurrencyPojoToCurrency : Mapper<CurrencyPojo, Currency> {
    override fun map(from: CurrencyPojo): Currency {
        return Currency(
            date = from.date,
            rate = from.rate,
            base = from.base,
            name = "",
            img = -1,
            isSelected = false
        )
    }
}


