package com.example.meteoauthentication.data.network

import com.example.meteoauthentication.data.responses.LoginResponse
import dagger.Provides
import retrofit2.http.GET

interface UserApi : BaseApi{
//    @GET("user")
//    suspend fun getUser(): LoginResponse
}