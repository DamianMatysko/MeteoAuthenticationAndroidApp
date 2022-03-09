package com.example.meteoauthentication.data.network

import com.example.meteoauthentication.model.LocalLoginRequest
import com.example.meteoauthentication.model.LocalRegisterRequest
import com.example.meteoauthentication.model.Authorization
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi : BaseApi {

    @POST("api/authentication/authenticate")
    suspend fun login(@Body localLoginRequest: LocalLoginRequest): Authorization

    @POST("api/authentication/register")
    suspend fun register(@Body localLoginRequest: LocalRegisterRequest):String //todo(change string)

    @POST("oauth2/authorization/google")
    suspend fun oauthSignIn(@Body string: String): Authorization

}