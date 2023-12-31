package com.example.decise.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decise.data.networks.ResponseMessage
import com.example.decise.data.networks.profile.DecisionGroup
import com.example.decise.data.networks.profile.changePassword.RequestChangePassword
import com.example.decise.data.networks.profile.decisionGroups.DecisionGroups
import com.example.decise.data.networks.profile.departments.Departments
import com.example.decise.data.networks.profile.designations.Designations
import com.example.decise.data.networks.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.data.networks.profile.update_personal_profile.RequestUpdatePersonalProfile
import com.example.decise.data.networks.profile.update_personal_profile.ResponseUpdatePersonalProfile
import com.example.decise.data.networks.subscription.subscriptionList.ResponseSubscriptionsList
import com.example.decise.repos.SecuredRepository
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val securedRepository: SecuredRepository
) : ViewModel() {
    val selectedItems = mutableSetOf<DecisionGroup>()

    private var _responseProfileData = MutableLiveData<NetworkResult<ResponsePersonalProfile>>()
    val profileDataVMLD: LiveData<NetworkResult<ResponsePersonalProfile>> = _responseProfileData

    fun getProfileData(id: Int) {
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

    //department list start

    private var _responseDepartments = MutableLiveData<NetworkResult<List<Departments>>>()
    val departmentsVMLD: LiveData<NetworkResult<List<Departments>>> = _responseDepartments

    fun getDepartments(id: Int) {
        _responseDepartments.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.getDepartmentListRepo(id)

                if (response.isSuccessful && response.body() != null) {
                    _responseDepartments.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseDepartments.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseDepartments.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }

    //department list end

    //Designations list start

    private var _responseDesignations = MutableLiveData<NetworkResult<List<Designations>>>()
    val designationsVMLD: LiveData<NetworkResult<List<Designations>>> = _responseDesignations

    fun getDesignations(id: Int) {
        _responseDesignations.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.getDesignationListRepo(id)

                if (response.isSuccessful && response.body() != null) {
                    _responseDesignations.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseDesignations.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseDesignations.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }

    //Designations list end
    //department list start

    private var _responseDecisionGroups = MutableLiveData<NetworkResult<List<DecisionGroups>>>()
    val decisionGroupsVMLD: LiveData<NetworkResult<List<DecisionGroups>>> = _responseDecisionGroups

    fun getDecisionGroups(id: Int) {
        _responseDecisionGroups.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.getDecisionGroupListRepo(id)

                if (response.isSuccessful && response.body() != null) {
                    _responseDecisionGroups.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseDecisionGroups.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseDecisionGroups.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }

    //department list end

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

    //updatePersonalProfile start

    private var _responseUpdatePersonalProfile =
        MutableLiveData<NetworkResult<ResponseUpdatePersonalProfile>>()
    val updatePersonalProfileVMLD: LiveData<NetworkResult<ResponseUpdatePersonalProfile>> =
        _responseUpdatePersonalProfile

    fun updatePersonalProfile(requestUpdatePersonalProfile: RequestUpdatePersonalProfile) {
        _responseUpdatePersonalProfile.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.updatePersonalProfile(requestUpdatePersonalProfile)

                if (response.isSuccessful && response.body() != null) {
                    _responseUpdatePersonalProfile.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseUpdatePersonalProfile.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseUpdatePersonalProfile.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }
    //updatePersonalProfile end

//change password start

    private var _responseChangePassword =
        MutableLiveData<NetworkResult<ResponseMessage>>()
    val responseChangePasswordVMLD: LiveData<NetworkResult<ResponseMessage>> =
        _responseChangePassword

    fun changePasswordVM(requestChangePassword: RequestChangePassword) {
        _responseChangePassword.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.changePassword(requestChangePassword)

                if (response.isSuccessful && response.body() != null) {
                    _responseChangePassword.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseChangePassword.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseChangePassword.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }

//change password end

    //change picture start

    private var _responseChangePicture =
        MutableLiveData<NetworkResult<ResponseMessage>>()
    val responseChangePictureVMLD: LiveData<NetworkResult<ResponseMessage>> =
        _responseChangePicture

    fun changePictureVM(part: MultipartBody.Part) {
        _responseChangePicture.postValue(NetworkResult.Loading())

        viewModelScope.launch {
            try {
                val response = securedRepository.changeProfilePic(part)
                if (response.isSuccessful && response.body() != null) {
                    _responseChangePicture.postValue(NetworkResult.Success(response.body()!!))
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _responseChangePicture.postValue(
                        NetworkResult.Error(
                            errorObj.getString("message")
                        )
                    )
                }
            } catch (noInternetException: NoInternetException) {
                _responseChangePicture.postValue(
                    NetworkResult.Error(
                        noInternetException.localizedMessage ?: "No Internet Connection"
                    )
                )
            }
        }
    }

//change picture end


}
