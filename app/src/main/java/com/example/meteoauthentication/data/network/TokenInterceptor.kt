package com.example.meteoauthentication.data.network

import android.content.Context
import com.example.meteoauthentication.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    context: Context,
    // private val tokenApi: TokenRefreshApi
) : Interceptor {

    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)


    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            val original = chain.request()
            val builder = original.newBuilder()
                .header("Authorization", "Bearer ${userPreferences.jwtToken.first()}")
                 chain.proceed(builder.build())
        }
    }

//    override fun authenticate(route: Route?, response: Response): Request? {
//
//        return runBlocking {
//            response.request.newBuilder()
//               .header("Authorization", "Bearer ${userPreferences.jwtToken.first()}")
//                .build()
////            when (userPreferences.jwtToken.first() != null){
////                is Resource.Success -> {
////                    response.request.newBuilder()
////                        .header("Authorization", "Bearer ${userPreferences.jwtToken.first()}")
////                        .build()
////                }
////                else -> null
////            }
//            when (val tokenResponse = getUpdatedToken()) {
//                is Resource.Success -> {
//                    userPreferences.saveAccessTokens(
//                        tokenResponse.value.access_token!!,
//                        tokenResponse.value.refresh_token!!
//                    )
//                    response.request.newBuilder()
//                        //.header("Authorization", "Bearer ${tokenResponse.value.access_token}")
//                        .header("Authorization", "Bearer ${tokenResponse.value.access_token}")
//                        .build()
//                }
//                else -> null
//            }
    //       }
//    }

//    private suspend fun getUpdatedToken(): Resource<TokenResponse> {
//        val refreshToken = userPreferences.refreshToken.first()
//        return safeApiCall { tokenApi.refreshAccessToken(refreshToken) }
//    }

}