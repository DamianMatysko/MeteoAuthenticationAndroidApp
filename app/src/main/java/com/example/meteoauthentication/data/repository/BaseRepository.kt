package com.example.meteoauthentication.data.repository

import com.example.meteoauthentication.data.network.BaseApi
import com.example.meteoauthentication.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}