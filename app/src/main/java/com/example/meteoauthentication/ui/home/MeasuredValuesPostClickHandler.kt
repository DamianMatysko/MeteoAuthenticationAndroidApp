package com.example.meteoauthentication.ui.home

import com.example.meteoauthentication.model.MeasuredValue


interface MeasuredValuesPostClickHandler {
    fun clickedMeasuredPostItem(measuredValue: MeasuredValue)
}