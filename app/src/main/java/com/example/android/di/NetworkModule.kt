package com.example.android.di

import com.example.android.data.data_source.api.CurrencyApiService
import com.example.android.data.data_source.api.helper.CurrencyRemoteException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://us-central1-epam-laba-13-1527598553135.cloudfunctions.net"
private const val CONNECT_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCurrencyApiService(): CurrencyApiService {

        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val headerInterceptor = Interceptor { chain ->
            try {
                val newRequest = chain.request()
                    .newBuilder()
                    .build()
                val response = chain.proceed(newRequest)

                if (response.code >= HttpURLConnection.HTTP_BAD_REQUEST && response.code <= 499) {
                    throw CurrencyRemoteException.ClientError(response.message)
                } else if (
                    response.code >= HttpURLConnection.HTTP_INTERNAL_ERROR && response.code <= 599
                ) {
                    throw CurrencyRemoteException.ServerError(response.message)
                }
                response
            } catch (e: Exception) {
                throw  when (e) {
                    is UnknownHostException -> CurrencyRemoteException.NoNetworkError(e.message.toString())
                    is SocketTimeoutException -> CurrencyRemoteException.NoNetworkError(e.message.toString())
                    is CurrencyRemoteException -> e
                    else -> CurrencyRemoteException.GenericError(e.message.toString())
                }
            }
        }

        val client = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(CurrencyApiService::class.java)
    }
}
