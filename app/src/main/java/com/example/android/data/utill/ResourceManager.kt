package com.example.android.data.utill

import android.content.Context

class ResourceManager(
    private val context: Context,
) {

    fun getDrawableId(name: String): Int {
        return context.resources.getIdentifier(
            "ic_${name.lowercase()}_flag",
            "drawable",
            context.packageName
        )
    }

    fun getStringResourceId(name: String): String {
        return try {
            val id = context.resources.getIdentifier(
                "currency_${name.lowercase()}_name",
                "string",
                context.packageName
            )
            context.getString(id)
        } catch (e: Exception) {
            ""
        }
    }
}
