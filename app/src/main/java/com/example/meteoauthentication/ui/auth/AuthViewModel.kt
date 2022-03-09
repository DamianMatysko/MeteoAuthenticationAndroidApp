package com.example.meteoauthentication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meteoauthentication.data.network.Resource
import com.example.meteoauthentication.data.repository.AuthRepository
import com.example.meteoauthentication.model.Authorization
import com.example.meteoauthentication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<Authorization>> = MutableLiveData()
    val loginResponse: LiveData<Resource<Authorization>>
        get() = _loginResponse

    private val _oauthSignInResponse: MutableLiveData<Resource<Authorization>> = MutableLiveData()
    val oauthSignInResponse: LiveData<Resource<Authorization>>
        get() = _oauthSignInResponse

    private val _registerResponse: MutableLiveData<Resource<Authorization>> = MutableLiveData()
    val registerResponse: LiveData<Resource<Authorization>>
        get() = _registerResponse


    fun login(
        username: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(username, password)
    }

    fun oauthSignIn(code: String) = viewModelScope.launch {
        _oauthSignInResponse.value = Resource.Loading
        _oauthSignInResponse.value = repository.oauthSignIn(code)
    }

    suspend fun saveToken(token: String) {
        repository.saveToken(token)
    }

    suspend fun saveAccessTokens(access_token: String, refresh_token: String) {
        repository.saveAccessTokens(access_token, refresh_token)
    }


//    suspend fun saveRefreshToken(token: String) {
//        repository.saveRefreshToken(token)
//    }

    suspend fun saveEmail(email: String) {
        repository.saveEmail(email)
    }

    fun isUserLogged(): Boolean {
        return repository.isUserLogged()
    }


    fun register(
        username: String,
        password: String,
        email: String
    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        repository.register(username, password, email)
    }

}