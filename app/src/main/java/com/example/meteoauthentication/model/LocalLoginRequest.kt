package com.example.meteoauthentication.model

import android.provider.ContactsContract
// todo change file name
data class LocalLoginRequest(val username: String,
                             val password: String)

data class LocalRegisterRequest(
    val username: String,
    val password: String,
    val email: String
)

data class Token(val jwt: String)