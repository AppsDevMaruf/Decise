package com.example.decise.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decise.data.models.login.RequestLogin
import com.example.decise.data.models.signUp.RequestSignUp
import com.example.decise.data.models.login.ResponseLogin
import com.example.decise.data.models.sendEmail.RequestSendEmail
import com.example.decise.data.models.sendEmail.ResponseSendEmail
import com.example.decise.data.models.signUp.ResponseSignUp
import com.example.decise.repos.PublicRepository
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val publicRepository: PublicRepository
) : ViewModel() {

    //  login  start

    private var _responseLogin =
        MutableLiveData<NetworkResult<ResponseLogin>>()
    val loginVMLD: LiveData<NetworkResult<ResponseLogin>>
        get() = _responseLogin

    @SuppressLint("SuspiciousIndentation")
    suspend fun loginUserVM(requestLogin: RequestLogin) {
        _responseLogin.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = publicRepository.loginRepo(requestLogin)

                if (response.isSuccessful && response.body() != null) {

                    _responseLogin.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseLogin.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseLogin.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }
        }

    }

    //  login  end

    //signup start


    private var _responseSignUp =
        MutableLiveData<NetworkResult<ResponseSignUp>>()
    val signUpVMLD: LiveData<NetworkResult<ResponseSignUp>>
        get() = _responseSignUp

    @SuppressLint("SuspiciousIndentation")
    suspend fun signUpVM(requestSignUp: RequestSignUp) {
        _responseSignUp.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = publicRepository.signUpRepo(requestSignUp)

                if (response.isSuccessful && response.body() != null) {

                    _responseSignUp.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseSignUp.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseSignUp.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }
        }

    }

    //  sendEmail


    //signup start


    private var _responseSendEmail =
        MutableLiveData<NetworkResult<ResponseSendEmail>>()
    val sendEmailVMLD: LiveData<NetworkResult<ResponseSendEmail>>
        get() = _responseSendEmail

    @SuppressLint("SuspiciousIndentation")
    suspend fun sendEmailVM(email: String) {
        _responseSendEmail.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = publicRepository.sendEmailRepo(email)

                if (response.isSuccessful && response.body() != null) {

                    _responseSendEmail.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseSendEmail.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseSendEmail.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }
        }

    }

    //  sendEmail


}