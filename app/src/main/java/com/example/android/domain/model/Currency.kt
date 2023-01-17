package com.example.android.domain.model

data class Currency(
    val date: Long,
    var rate: Double,
    val base: String,
    var name: String,
    var img: Int,
    var isSelected: Boolean
)