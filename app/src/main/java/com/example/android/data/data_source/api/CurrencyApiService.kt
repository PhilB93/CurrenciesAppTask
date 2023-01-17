package com.example.android.data.data_source.api

import com.example.android.data.data_source.api.pojo.CurrencyPojo
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {

    @GET("/myWebsiteBackend/api/currency/{base}")
    suspend fun getCurrency(
        @Path("base") base: String,
    ): CurrencyPojo

    @GET("/myWebsiteBackend/api/currency")
    suspend fun getAllCurrency(): List<CurrencyPojo>

}