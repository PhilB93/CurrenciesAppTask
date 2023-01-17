package com.example.android.domain.utill

sealed class Record<out R : Any> {
    object Loading : Record<Nothing>()
    object Empty : Record<Nothing>()
    data class Success<out T : Any>(val data: T) : Record<T>()
    data class Error(val error: ErrorRecord) : Record<Nothing>()
}
