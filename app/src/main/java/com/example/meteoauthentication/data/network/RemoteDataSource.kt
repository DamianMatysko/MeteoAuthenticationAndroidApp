package com.example.meteoauthentication.data.network

import android.content.Context
import com.example.meteoauthentication.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

    companion object {
        //private const val BASE_URL = "http://192.168.1.2:9090" todo(dynamic IP problem)
        private const val BASE_URL = "http://192.168.1.146:9090"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        val interceptor = TokenInterceptor(context)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient(interceptor, context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }


    private fun getRetrofitClient(authenticator: Interceptor? = null, context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())}

            .addInterceptor(TokenInterceptor(context))
            .also { client ->

                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}