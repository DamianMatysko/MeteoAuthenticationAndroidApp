package com.example.meteoauthentication.ui.home

import com.example.meteoauthentication.model.GetUserStationResponse


interface PostClickHandler {
    fun clickedPostItem(getUserStationResponse: GetUserStationResponse)
}