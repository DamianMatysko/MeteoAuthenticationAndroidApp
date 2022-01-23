package com.example.meteoauthentication.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "my_data_store")

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

    suspend fun saveToken(token: String) {
        appContext.dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = token
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

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
        private val JWT_TOKEN = stringPreferencesKey("key_jwt")
        private val EMAIL = stringPreferencesKey("email")
    }

}