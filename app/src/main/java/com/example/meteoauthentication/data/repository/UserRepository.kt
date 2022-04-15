package com.example.meteoauthentication.data.repository

import android.content.Context
import com.example.meteoauthentication.data.UserPreferences
import com.example.meteoauthentication.data.network.UserApi
import com.example.meteoauthentication.model.AddStationRequest
import com.example.meteoauthentication.model.UserUpdate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi,
    @ApplicationContext context: Context
) : BaseRepository(api) {
    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)

    suspend fun updateUser(
        newUsername: String,
        newPassword: String,
        newEmail: String,
        newCity: String
    ) = safeApiCall {
        var currentEmail = "not found"
        userPreferences.email.first().let {
            if (it != null) {
                currentEmail = it
            }
        }
        api.updateUser(
            currentEmail,
            UserUpdate(newUsername, newPassword, newEmail, newCity)
        )


    }

    suspend fun addStation(
        title: String,
        destination: String,
        modelDescription: String,
        phone: Number?
    ) = safeApiCall {
        api.addStation(AddStationRequest(title,destination,modelDescription,phone))
    }

    suspend fun getUserStations() = safeApiCall {
        api.getUserStations()
    }


    suspend fun getStationToken(id: Number) = safeApiCall {
        api.getStationToken(id)
    }

    suspend fun deleteStation(id: Number) = safeApiCall {
        api.deleteStation(id)
    }

    suspend fun getMeasuredValuesById(id: Number) = safeApiCall {
        api.getMeasuredValuesById(id)
    }

    suspend fun getUser() = safeApiCall {
        var currentEmail = "not found"
        userPreferences.email.first().let {
            if (it != null) {
                currentEmail = it
            }
        }
        api.getUser(currentEmail)
    }

}



