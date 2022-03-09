package com.example.meteoauthentication.data.network

import com.example.meteoauthentication.model.Authorization
import com.example.meteoauthentication.model.RefreshTokenRequest
import retrofit2.http.*

interface TokenRefreshApi : BaseApi {

    @POST("api/authentication/refreshToken")
    suspend fun refreshAccessToken(
     //   @Header("Authorization") authorization: String?
      //todo  @Header("refresh_token") authorization: String?
       @Body refreshTokenRequest: RefreshTokenRequest
    ): Authorization //TokenResponse
}