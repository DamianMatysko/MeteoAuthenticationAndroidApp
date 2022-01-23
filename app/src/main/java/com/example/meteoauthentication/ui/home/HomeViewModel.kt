package com.example.meteoauthentication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.data.repository.UserRepository
import com.example.meteoauthentication.data.responses.LoginResponse
import com.example.meteoauthentication.model.GetUserStationResponse
import com.example.meteoauthentication.ui.base.BaseViewModel
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {


    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
//    val user: LiveData<Resource<LoginResponse>>
//        get() = _user

//    todo(user/me isn't working in api)
//    fun getUser() = viewModelScope.launch {
//        _user.value = Resource.Loading
//        _user.value = repository.getUser()
//    }


    private val _updateResponse: MutableLiveData<Resource<String>> = MutableLiveData()
    val updateResponse: LiveData<Resource<String>>
        get() = _updateResponse

    private val _getUserStationsResponse: MutableLiveData<Resource<ArrayList<GetUserStationResponse>>> = MutableLiveData()
    val getUserStationsResponse: LiveData<Resource<ArrayList<GetUserStationResponse>>>
        get() = _getUserStationsResponse

//    private val _getUserStationsResponse: MutableLiveData<Resource<JsonArray>> = MutableLiveData()
//    val getUserStationsResponse: LiveData<Resource<JsonArray>>
//        get() = _getUserStationsResponse

    fun updateUser(
        newUsername: String,
        newPassword: String,
        newEmail: String,
        newCity: String
    ) = viewModelScope.launch {
        repository.updateUser(newUsername, newPassword, newEmail, newCity)
    }

    fun getUserStations() = viewModelScope.launch {
        val resolute =repository.getUserStations()
        _getUserStationsResponse.value = resolute
    }


}