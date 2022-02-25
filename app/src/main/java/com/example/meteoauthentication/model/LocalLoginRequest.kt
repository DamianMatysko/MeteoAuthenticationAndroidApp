package com.example.meteoauthentication.model

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

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
) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun toString(): String =
        "Measurement time: ${formatDate(measurement_time)}, Humidity: $humidity, Temperature: $temperature, Air quality: $air_quality, Wind speed: $wind_speed, Wind gusts: $wind_gusts, Wind direction: $wind_direction, Rainfall: $rainfall"
}

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

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.N)
fun formatDate(time: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val date = dateFormat.parse(time)
    val formatter = SimpleDateFormat("dd.MM.yyyy hh:mm")
    val dateStr = formatter.format(date)
    return dateStr
}