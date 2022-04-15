package com.example.meteoauthentication.data.repository

import com.example.meteoauthentication.data.UserPreferences
import com.example.meteoauthentication.data.network.AuthApi
import com.example.meteoauthentication.model.LocalLoginRequest
import com.example.meteoauthentication.model.LocalRegisterRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(api) {

    suspend fun login(
        username: String,
        password: String
    ) = safeApiCall {
        api.login(LocalLoginRequest(username, password))
    }

    suspend fun saveAccessTokens(access_token: String, refresh_token: String) {
        preferences.saveAccessTokens(access_token, refresh_token)
    }

    fun isUserLogged(): Boolean {
        return preferences.isUserLogged()
    }

    suspend fun saveEmail(email: String) {
        preferences.saveEmail(email)
    }

    suspend fun register(
        username: String,
        password: String,
        email: String
    ) = safeApiCall {
        api.register(LocalRegisterRequest(username, password, email))
    }

    suspend fun oauthSignIn(code: String) = safeApiCall {
        api.oauthSignIn(code)
    }

}