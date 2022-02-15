package com.example.meteoauthentication.data.network

import com.example.meteoauthentication.model.*
import retrofit2.http.*

interface UserApi : BaseApi {
    @PUT("api/users/{email}")
    suspend fun updateUser(@Path("email") email: String, @Body userUpdate: UserUpdate): User
    @GET("/api/stations/byUser")
    suspend fun getUserStations():ArrayList<GetUserStationResponse>
    @GET("/api/authentication/authenticate-station/{id}")
    suspend fun getStationToken(@Path("id") id: Number) : Token
    @GET("api/users/{email}")
    suspend fun getUser(@Path("email") email: String): User
    @GET("/api/measured_values/by-station/{id}")
    suspend fun getMeasuredValuesById(@Path("id") id: Number) : ArrayList<MeasuredValue>
    @POST("/api/stations/add")
    suspend fun addStation(@Body addStationRequest: AddStationRequest): GetUserStationResponse
    @DELETE("/api/stations/{id}")
    suspend fun deleteStation(@Path("id") id:Number): Any

}