package com.example.meteoauthentication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.data.repository.UserRepository
import com.example.meteoauthentication.data.responses.LoginResponse
import com.example.meteoauthentication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {

//    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
//    val user: LiveData<Resource<LoginResponse>>
//        get() = _user

//    fun getUser() = viewModelScope.launch {
//        _user.value = Resource.Loading
//        _user.value = repository.getUser()
//    }

}