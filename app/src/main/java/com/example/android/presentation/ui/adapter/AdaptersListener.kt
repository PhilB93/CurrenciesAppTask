package com.example.android.presentation.ui.adapter

import com.example.android.domain.model.Currency


interface AdaptersListener {
    fun onClickItem(currency: Currency)
}
