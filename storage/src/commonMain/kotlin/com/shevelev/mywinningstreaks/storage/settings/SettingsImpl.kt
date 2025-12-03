package com.shevelev.mywinningstreaks.storage.settings

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shevelev.mywinningstreaks.storage.settings.factory.SettingsFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

internal class SettingsImpl(
    private val factory: SettingsFactory,
): Settings {
    private val dataStore by lazy { factory.create() }

    override suspend fun readString(key: String): String? = read(key)

    override suspend fun readInt(key: String): Int? = read(key)

    override suspend fun readBoolean(key: String): Boolean? = read(key)

    override suspend fun readFloat(key: String): Float? = read(key)

    override suspend fun putString(key: String, value: String?) {
        if(value == null) remove<String>(key) else put(key, value)
    }

    override suspend fun putInt(key: String, value: Int?) {
        if(value == null) remove<Int>(key) else put(key, value)
    }

    override suspend fun putBoolean(key: String, value: Boolean?) {
        if(value == null) remove<Boolean>(key) else put(key, value)
    }

    override suspend fun putFloat(key: String, value: Float?) {
        if(value == null) remove<Float>(key) else put(key, value)
    }

    private suspend inline fun <reified T> read(key: String): T? {
        return dataStore.data.map { preferences ->
            when (T::class) {
                String::class -> preferences[stringPreferencesKey(key)] as T?
                Int::class -> preferences[intPreferencesKey(key)] as T?
                Boolean::class -> preferences[booleanPreferencesKey(key)] as T?
                Float::class -> preferences[floatPreferencesKey(key)] as T?
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }.firstOrNull()
    }

    private suspend inline fun <reified  T>put(key: String, value: T) {
        dataStore.edit { preferences ->
            when(value) {
                is String -> preferences[stringPreferencesKey(key)] = value as String
                is Int -> preferences[intPreferencesKey(key)] = value as Int
                is Boolean -> preferences[booleanPreferencesKey(key)] = value as Boolean
                is Float -> preferences[floatPreferencesKey(key)] = value as Float
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    private suspend inline fun <reified  T>remove(key: String) {
        dataStore.edit { preferences ->
            when(T::class) {
                String::class -> preferences.remove(stringPreferencesKey(key))
                Int::class -> preferences.remove(intPreferencesKey(key))
                Boolean::class -> preferences.remove(booleanPreferencesKey(key))
                Float::class -> preferences.remove(floatPreferencesKey(key))
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }
}