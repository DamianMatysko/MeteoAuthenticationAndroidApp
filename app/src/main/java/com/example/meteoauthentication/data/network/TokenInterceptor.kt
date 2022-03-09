package com.example.meteoauthentication.data.network

import android.content.Context
import com.example.meteoauthentication.data.UserPreferences
import com.example.meteoauthentication.model.Authorization
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    context: Context,
) : Interceptor, SafeApiCall  {

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

}