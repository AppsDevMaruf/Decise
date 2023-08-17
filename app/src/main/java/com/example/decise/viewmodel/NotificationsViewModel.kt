package com.example.decise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decise.data.models.notifications.ResponseFindByCompanyIdAndStatus
import com.example.decise.repos.SecuredRepository
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val securedRepository: SecuredRepository
) : ViewModel() {

//GetNotificationByCompanyIdAndStatus start

    private var _responseGetNotificationByCompanyIdAndStatus =
        MutableLiveData<NetworkResult<ResponseFindByCompanyIdAndStatus>>()
    val getNotificationByCompanyIdAndStatusVMLD: LiveData<NetworkResult<ResponseFindByCompanyIdAndStatus>> =
        _responseGetNotificationByCompanyIdAndStatus

    fun getNotificationByCompanyIdAndStatusVM(
        companyId: Int,
        status: Boolean,
        page: Int,
        size: Int
    ) {
        _responseGetNotificationByCompanyIdAndStatus.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.getNotificationByCompanyIdAndStratusRepo(
                    companyId,
                    status,
                    page,
                    size
                )

                if (response.isSuccessful && response.body() != null) {
                    _responseGetNotificationByCompanyIdAndStatus.postValue(
                        NetworkResult.Success(
                            response.body()!!
                        )
                    )
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseGetNotificationByCompanyIdAndStatus.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseGetNotificationByCompanyIdAndStatus.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }
}
//GetNotificationByCompanyIdAndStatus end