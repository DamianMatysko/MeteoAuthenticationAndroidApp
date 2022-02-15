package com.example.meteoauthentication.model

// todo change file name
data class LocalLoginRequest(
    val username: String,
    val password: String
)

data class LocalRegisterRequest(
    val username: String,
    val password: String,
    val email: String
)

//todo(move to responses)

data class Token(val jwt: String)

data class UserUpdate(
    val username: String,
    val password: String,
    val email: String,
    val city: String
)

data class MeasuredValue(
    val id: String,
    val measurement_time: String,
    val humidity: String,
    val temperature: String,
    val air_quality: String,
    val wind_speed: String,
    val wind_gusts: String,
    val wind_direction: String,
    val rainfall: String
)


data class GetUserStationResponse(
    val id: Number,
    var title: String,
    val destination: String,
    val model_description: String,
    val registration_time: String,
    val phone: Number?,
    val user: User
)

data class User(
    val username: String,
    val email: String,
    val city: String
)

data class AddStationRequest(
    var title: String,
    val destination: String,
    val model_description: String,
    val phone: Number?
)