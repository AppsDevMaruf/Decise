package com.example.decise.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decise.data.models.auth.forgetPassword.ResponseForgetPassword
import com.example.decise.data.models.auth.login.RequestLogin
import com.example.decise.data.models.auth.login.ResponseLogin
import com.example.decise.data.models.auth.verifyEmail.ResponseVerifyEmail
import com.example.decise.data.models.auth.signUp.RequestSignUp
import com.example.decise.data.models.auth.sendEmail.ResponseSendEmail
import com.example.decise.data.models.auth.signUp.ResponseSignUp
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

    private var _responseLogin = MutableLiveData<NetworkResult<ResponseLogin>>()
    val loginVMLD: LiveData<NetworkResult<ResponseLogin>> = _responseLogin
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
    private var _responseSignUp = MutableLiveData<NetworkResult<ResponseSignUp>>()
    val completeSignUpVMLD: LiveData<NetworkResult<ResponseSignUp>> = _responseSignUp
    @SuppressLint("SuspiciousIndentation")
    fun completeSignUpVM(requestSignUp: RequestSignUp) {
        _responseSignUp.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = publicRepository.completeSignUpRepo(requestSignUp)

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
    //  signup end


    //sendEmail start

    private val _responseSendEmail = MutableLiveData<NetworkResult<ResponseSendEmail>>()
    val responseSendEmail: LiveData<NetworkResult<ResponseSendEmail>> = _responseSendEmail

    @SuppressLint("SuspiciousIndentation")
    fun sendEmailVM(email: String) {
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

    //  verifyEmail end

    private var _responseVerifyEmail = MutableLiveData<NetworkResult<ResponseVerifyEmail>>()
    val verifyEmailVMLD: LiveData<NetworkResult<ResponseVerifyEmail>> = _responseVerifyEmail


    fun verifyEmailVM(
        subscriptionType: String,
        signupType: String,
        email: String,
        evc: String
    ) {
        _responseVerifyEmail.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response =
                    publicRepository.verifyEmailRepo(subscriptionType, signupType, email, evc)

                if (response.isSuccessful && response.body() != null) {

                    _responseVerifyEmail.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseVerifyEmail.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseVerifyEmail.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }
        }

    }

    //  verifyEmail end


    //forgetPassword start

    private val _responseForgetPassword = MutableLiveData<NetworkResult<ResponseForgetPassword>>()
    val responseForgetPassword: LiveData<NetworkResult<ResponseForgetPassword>> = _responseForgetPassword

    @SuppressLint("SuspiciousIndentation")
    fun forgetPasswordVM(email: String) {
        _responseForgetPassword.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = publicRepository.forgotPasswordRepo(email)

                if (response.isSuccessful && response.body() != null) {

                    _responseForgetPassword.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseForgetPassword.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseForgetPassword.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }
        }

    }

    //  forgetPassword end


}