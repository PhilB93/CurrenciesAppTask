package com.example.android.data.data_source.api.pojo


import com.squareup.moshi.Json

data class CurrencyPojo(
    @Json(name = "date") val date: Long,
    @Json(name = "rate") val rate: Double,
    @Json(name = "base") val base: String
)