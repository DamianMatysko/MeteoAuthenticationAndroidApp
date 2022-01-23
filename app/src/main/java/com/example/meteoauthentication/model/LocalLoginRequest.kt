package com.example.meteoauthentication.model

import com.example.meteoauthentication.data.responses.User
import java.security.Timestamp

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

data class GetUserStationResponse(
    val id: Number,
    var title: String,
    val destination: String,
    val model_description: String,
    val registration_time: String,
    val phone: Number?,
    val user: com.example.meteoauthentication.model.User
)

data class User(
    val username: String,
    val email: String,
    val city: String
)