package com.example.android.data.mappers

import android.util.Log
import com.example.android.data.data_source.api.helper.CurrencyRemoteException
import com.example.android.domain.utill.ErrorRecord
import com.example.android.domain.utill.Record
import javax.inject.Inject

class ErrorMapper @Inject constructor() {
    fun mapErrorRecord(e: CurrencyRemoteException): Record.Error {
        Log.e(ErrorMapper::class.simpleName, e.message.toString())
        val errorRecord: ErrorRecord = when (e) {
            is CurrencyRemoteException.ClientError -> ErrorRecord.ClientError
            is CurrencyRemoteException.ServerError -> ErrorRecord.ServerError
            is CurrencyRemoteException.NoNetworkError -> ErrorRecord.NetworkError
            else -> ErrorRecord.GenericError
        }
        return Record.Error(errorRecord)
    }
}
