package com.example.decise.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decise.data.networks.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.networks.profile.chooseSubscriptionType.ResponseChooseSubscriptionType
import com.example.decise.data.networks.subscription.subscriptionList.ResponseSubscriptionsList
import com.example.decise.repos.SecuredRepository
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val securedRepository: SecuredRepository
) : ViewModel() {

    private val _chooseSubscriptionResponse = MutableLiveData<RequestChooseSubscriptionType>()
    val chooseSubscriptionResponse: LiveData<RequestChooseSubscriptionType> =
        _chooseSubscriptionResponse

    fun setChooseSubscriptionResponse(response: RequestChooseSubscriptionType) {
        _chooseSubscriptionResponse.postValue(response)
    }

    //  getSubscriptionList start

    private var _responseGetSubscriptionList =
        MutableLiveData<NetworkResult<ResponseSubscriptionsList>>()
    val getSubscriptionListVMLD: LiveData<NetworkResult<ResponseSubscriptionsList>> =
        _responseGetSubscriptionList

    @SuppressLint("SuspiciousIndentation")
    fun getSubscriptionListVM() {
        _responseGetSubscriptionList.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.getSubscriptionListRepo()

                if (response.isSuccessful && response.body() != null) {

                    _responseGetSubscriptionList.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseGetSubscriptionList.postValue(NetworkResult.Error(errorObj.getString("message")))

                }
            } catch (noInternetException: NoInternetException) {
                _responseGetSubscriptionList.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(
                        it
                    )
                })
            }
        }

    }

    //  getSubscriptionList   end


    //  chooseSubscriptionType start

    private var _responseChooseSubscriptionType =
        MutableLiveData<NetworkResult<ResponseChooseSubscriptionType>>()
    val chooseSubscriptionTypeVMLD: LiveData<NetworkResult<ResponseChooseSubscriptionType>> =
        _responseChooseSubscriptionType

    @SuppressLint("SuspiciousIndentation")
    fun chooseSubscriptionTypeVM(request: RequestChooseSubscriptionType) {
        _responseChooseSubscriptionType.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.chooseSubscriptionTypeRepo(request)

                if (response.isSuccessful && response.body() != null) {

                    _responseChooseSubscriptionType.postValue(NetworkResult.Success(response.body()!!))

                } else if (response.errorBody() != null) {

                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseChooseSubscriptionType.postValue(
                        NetworkResult.Error(errorObj.getString("message"))
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

    //  chooseSubscriptionType   end


}


