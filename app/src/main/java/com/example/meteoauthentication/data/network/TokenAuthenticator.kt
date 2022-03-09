package com.example.meteoauthentication.data.network;

import android.content.Context
import android.util.Log
import com.example.meteoauthentication.data.UserPreferences
import com.example.meteoauthentication.data.repository.BaseRepository
import com.example.meteoauthentication.model.Authorization
import com.example.meteoauthentication.model.RefreshTokenRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

private const val TAG = "TokenAuthenticator"
class TokenAuthenticator @Inject constructor(
    context: Context,
    private val tokenApi: TokenRefreshApi
) : Authenticator, BaseRepository(tokenApi) {

    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val tokenResponse = getUpdatedToken()) {
                is Resource.Success -> {
                    userPreferences.saveAccessTokens(
                        tokenResponse.value.jwt,
                        tokenResponse.value.refreshToken
                    )
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokenResponse.value.jwt}")
                        .build()
                }
                else -> null
            }
        }
    }

    private suspend fun getUpdatedToken(): Resource<Authorization> {
        val refreshToken = RefreshTokenRequest("Bearer ${userPreferences.refreshToken.first()}")
        Log.d(TAG, "getUpdatedToken: $refreshToken")
        return safeApiCall { tokenApi.refreshAccessToken(refreshToken) }
    }
}