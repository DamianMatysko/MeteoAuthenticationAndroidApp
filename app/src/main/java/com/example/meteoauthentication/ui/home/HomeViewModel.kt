package com.example.meteoauthentication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.data.repository.UserRepository
import com.example.meteoauthentication.model.GetUserStationResponse
import com.example.meteoauthentication.model.MeasuredValue
import com.example.meteoauthentication.model.Authorization
import com.example.meteoauthentication.model.User
import com.example.meteoauthentication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val communicator: Communicator,
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _getUserResponse: MutableLiveData<Resource<User>> = MutableLiveData()
    val getUserResponse: LiveData<Resource<User>>
        get() = _getUserResponse

    private val _updateResponse: MutableLiveData<Resource<User>> = MutableLiveData()
    val updateResponse: LiveData<Resource<User>>
        get() = _updateResponse

    private val _addStationResponse: MutableLiveData<Resource<GetUserStationResponse>> =
        MutableLiveData()
    val addStationResponse: LiveData<Resource<GetUserStationResponse>>
        get() = _addStationResponse

    private val _getUserStationsResponse: MutableLiveData<Resource<ArrayList<GetUserStationResponse>>> =
        MutableLiveData()
    val getUserStationsResponse: LiveData<Resource<ArrayList<GetUserStationResponse>>>
        get() = _getUserStationsResponse

    private val _getStationAuthorizationResponse: MutableLiveData<Resource<Authorization>> = MutableLiveData()
    val getStationAuthorizationResponse: LiveData<Resource<Authorization>>
        get() = _getStationAuthorizationResponse

    private val _deleteStationResponse: MutableLiveData<Resource<Any>> = MutableLiveData()
    val deleteStationResponse: LiveData<Resource<Any>>
        get() = _deleteStationResponse

    private val _getMeasuredValuesResponse: MutableLiveData<Resource<ArrayList<MeasuredValue>>> =
        MutableLiveData()
    val getMeasuredValuesResponse: LiveData<Resource<ArrayList<MeasuredValue>>>
        get() = _getMeasuredValuesResponse

    fun updateUser(
        newUsername: String,
        newPassword: String,
        newEmail: String,
        newCity: String
    ) = viewModelScope.launch {
        _updateResponse.value = Resource.Loading
        _updateResponse.value = repository.updateUser(newUsername, newPassword, newEmail, newCity)
    }

    fun addStation(
        title: String,
        destination: String,
        modelDescription: String,
        phone: Number?
    ) = viewModelScope.launch {
        _addStationResponse.value = Resource.Loading
        _addStationResponse.value =
            repository.addStation(title, destination, modelDescription, phone)
    }

    fun getUserStations() = viewModelScope.launch {
        val resolute = repository.getUserStations()
        _getUserStationsResponse.value = resolute
    }


    fun setSelectedUserStation(station: GetUserStationResponse) {
        communicator.getUserStationResponse = station
    }

    fun getSelectedUserStation() = communicator.getUserStationResponse

    fun getStationToken(id: Number) = viewModelScope.launch {
        _getStationAuthorizationResponse.value = Resource.Loading
        _getStationAuthorizationResponse.value = repository.getStationToken(id)
    }

    fun deleteStation(id: Number) = viewModelScope.launch {
        _deleteStationResponse.value = Resource.Loading
        _deleteStationResponse.value = repository.deleteStation(id)
    }

    fun getMeasuredValuesById(id: Number) = viewModelScope.launch {
        _getMeasuredValuesResponse.value = Resource.Loading
        _getMeasuredValuesResponse.value = repository.getMeasuredValuesById(id)
    }

    fun getUser() = viewModelScope.launch {
        _getUserResponse.value = Resource.Loading
        _getUserResponse.value = repository.getUser()
    }
}