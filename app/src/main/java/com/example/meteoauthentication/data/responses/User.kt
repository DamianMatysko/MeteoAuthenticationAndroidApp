package com.example.meteoauthentication.data.responses

data class User( //todo(delete/ repair )
    val access_token: String?,
    val refresh_token: String?,
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val name: String,
    val updated_at: String
)