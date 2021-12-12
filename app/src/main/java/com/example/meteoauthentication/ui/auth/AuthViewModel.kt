package com.example.meteoauthentication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.data.repository.AuthRepository
import com.example.meteoauthentication.model.Token
import com.example.meteoauthentication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<Token>> = MutableLiveData()
    val loginResponse: LiveData<Resource<Token>>
        get() = _loginResponse

    fun login(
        username: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(username, password)
    }

//    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) { todo
//        repository.saveAccessTokens(accessToken, refreshToken)
//    }
    suspend fun saveToken(token: String) {
        repository.saveToken(token)
    }


    private val _registerResponse: MutableLiveData<Resource<Token>> = MutableLiveData()
    val registerResponse: LiveData<Resource<Token>>
        get() = _registerResponse

    fun register(
        username: String,
        password: String,
        email: String
    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        repository.register(username, password, email)
    }

}