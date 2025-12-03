package com.shevelev.mywinningstreaks.storage.settings

interface Settings {
    suspend fun readString(key: String): String?

    suspend fun readInt(key: String): Int?

    suspend fun readBoolean(key: String): Boolean?

    suspend fun readFloat(key: String): Float?

    suspend fun putString(key: String, value: String?)

    suspend fun putInt(key: String, value: Int?)

    suspend fun putBoolean(key: String, value: Boolean?)

    suspend fun putFloat(key: String, value: Float?)
}