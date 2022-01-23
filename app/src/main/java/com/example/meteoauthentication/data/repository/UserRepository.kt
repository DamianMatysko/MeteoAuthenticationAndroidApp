package com.example.meteoauthentication.data.repository

import android.content.Context
import com.example.meteoauthentication.data.UserPreferences
import com.example.meteoauthentication.data.network.UserApi
import com.example.meteoauthentication.model.UserUpdate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.security.AccessControlContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi,
    @ApplicationContext context: Context
) : BaseRepository(api) {

    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)


//   suspend fun getUser() = safeApiCall { api.getUser() } //todo

    suspend fun updateUser(
        newUsername: String,
        newPassword: String,
        newEmail: String,
        newCity: String
    ) =
        safeApiCall {
            userPreferences.email.first()?.let {
                api.updateUser(
                    it,//"help@help.com",
                    UserUpdate(newUsername, newPassword, newEmail, newCity)
                )
            }
        }

    suspend fun getUserStations() = safeApiCall {
        api.getUserStations()
    }

}