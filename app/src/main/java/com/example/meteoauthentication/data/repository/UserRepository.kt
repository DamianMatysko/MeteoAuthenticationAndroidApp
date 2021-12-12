package com.example.meteoauthentication.data.repository

import com.example.meteoauthentication.data.network.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi
) : BaseRepository(api) {

//   suspend fun getUser() = safeApiCall { api.getUser() }

}