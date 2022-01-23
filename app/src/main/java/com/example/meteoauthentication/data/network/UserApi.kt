package com.example.meteoauthentication.data.network

import com.example.meteoauthentication.model.GetUserStationResponse
import com.example.meteoauthentication.model.UserUpdate
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi : BaseApi {

    @PUT("api/users/{email}")
    suspend fun updateUser(@Path("email") email: String, @Body userUpdate: UserUpdate): String?
    @GET("/api/stations/byUser")
    suspend fun getUserStations():ArrayList<GetUserStationResponse>


//    @GET("user")
//    suspend fun getUser(): LoginResponse todo
}