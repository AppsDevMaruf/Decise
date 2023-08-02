package com.example.decise.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decise.data.models.auth.forgetPassword.ResponseForgetPassword
import com.example.decise.data.models.auth.login.RequestLogin
import com.example.decise.data.models.auth.verifyEmail.ResponseVerifyEmail
import com.example.decise.data.models.auth.signUp.RequestSignUp
import com.example.decise.data.models.auth.login.ResponseLogin
import com.example.decise.data.models.auth.sendEmail.ResponseSendEmail
import com.example.decise.data.models.auth.signUp.ResponseSignUp
import com.example.decise.data.models.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.data.models.subscription.subscriptionList.ResponseSubscriptionsList
import com.example.decise.repos.PublicRepository
import com.example.decise.repos.SecuredRepository
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val securedRepository: SecuredRepository
) : ViewModel() {
    private var _responseProfileData = MutableLiveData<NetworkResult<ResponsePersonalProfile>>()
    val profileDataVMLD: LiveData<NetworkResult<ResponsePersonalProfile>> = _responseProfileData


    fun getProfileData(id:Int) {
        _responseProfileData.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.getPersonalProfileRepo(id)

                if (response.isSuccessful && response.body() != null) {
                    _responseProfileData.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseProfileData.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseProfileData.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }

//  choose Subscription type start

    private var _responseChooseSubscriptionType =
        MutableLiveData<NetworkResult<ResponseSubscriptionsList>>()
    val chooseSubscriptionTypeVMLD: LiveData<NetworkResult<ResponseSubscriptionsList>> =
        _responseChooseSubscriptionType

    @SuppressLint("SuspiciousIndentation")
    fun chooseSubscriptionTypeVM() {
        _responseChooseSubscriptionType.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.getSubscriptionListRepo()

                if (response.isSuccessful && response.body() != null) {

                    _responseChooseSubscriptionType.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseChooseSubscriptionType.postValue(
                        NetworkResult.Error(
                            errorObj.getString(
                                "message"
                            )
                        )
                    )

                }
            } catch (noInternetException: NoInternetException) {
                _responseChooseSubscriptionType.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }
        }

    }

    //  choose Subscription type   end


}