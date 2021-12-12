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

//    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
//        preferences.saveAccessTokens(accessToken, refreshToken)
//    }
    suspend fun saveToken(token: String) {
        preferences.saveToken(token)
    }

    suspend fun register(
        username: String,
        password: String,
        email: String
    ) = safeApiCall {
        api.register(LocalRegisterRequest(username,password,email))
    }
}