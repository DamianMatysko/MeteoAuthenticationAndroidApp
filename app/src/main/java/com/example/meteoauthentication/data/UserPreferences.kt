package com.example.meteoauthentication.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = "my_data_store"
)
private const val TAG = "UserPreferences"

class UserPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext

    val accessToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }

    val refreshToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }

    val jwtToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[JWT_TOKEN]
        }

    val email: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[EMAIL]
        }

    suspend fun saveAccessTokens(access_token: String, refresh_token: String) {
        appContext.dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = access_token
            preferences[REFRESH_TOKEN] = refresh_token
            Log.d(TAG, "saveAccessTokens: $refresh_token")
        }
    }

    suspend fun saveEmail(email: String) {
        appContext.dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    suspend fun clear() {
        appContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun isUserLogged(): Boolean {
        var result = false
        runBlocking(Dispatchers.IO) {
            result = appContext.dataStore.data.map { preferences ->
                return@map preferences[REFRESH_TOKEN] != null
            }.first()
        }
        return result
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
        private val JWT_TOKEN = stringPreferencesKey("key_jwt")
        private val EMAIL = stringPreferencesKey("email")
    }

}