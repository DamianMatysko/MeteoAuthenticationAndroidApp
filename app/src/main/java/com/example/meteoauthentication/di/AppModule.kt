package com.example.meteoauthentication.di

import android.content.Context
import com.example.meteoauthentication.data.network.AuthApi
import com.example.meteoauthentication.data.network.RemoteDataSource
import com.example.meteoauthentication.data.network.UserApi
import com.example.meteoauthentication.ui.home.Communicator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): AuthApi {
        return remoteDataSource.buildApi(AuthApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideUserApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): UserApi {
        return remoteDataSource.buildApi(UserApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideCommunicator(): Communicator {
        return Communicator()
    }


}