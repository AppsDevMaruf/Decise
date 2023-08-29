package com.example.decise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decise.data.networks.people.ResponseMemberList
import com.example.decise.repos.SecuredRepository
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {


    private var _responseMemberList = MutableLiveData<NetworkResult<ResponseMemberList>>()
    val responseMemberList: LiveData<NetworkResult<ResponseMemberList>> = _responseMemberList

    private fun <T> handleApiCall(
        apiCall: suspend () -> Response<T>,
        resultLiveData: MutableLiveData<NetworkResult<T>>
    ) {
        resultLiveData.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = apiCall()
                if (response.isSuccessful && response.body() != null) {
                    resultLiveData.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    resultLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
                }
            } catch (noInternetException: NoInternetException) {
                resultLiveData.postValue(noInternetException.localizedMessage?.let {
                    NetworkResult.Error(it)
                })
            }
        }
    }


    fun getMemberListVM() {
        handleApiCall(
            apiCall = { securedRepository.getMemberList() },
            resultLiveData = _responseMemberList
        )
    }


    /*
        fun chooseSubscriptionTypeVM(request: RequestChooseSubscriptionType) {
            handleApiCall(
                apiCall = { securedRepository.chooseSubscriptionTypeRepo(request) },
                resultLiveData = _responseChooseSubscriptionType
            )
        }
    */


}
