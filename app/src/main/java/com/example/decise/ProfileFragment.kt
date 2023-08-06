package com.example.decise

import androidx.fragment.app.viewModels
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.databinding.FragmentProfileBinding
import com.example.decise.utils.Constants
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.TokenManager
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val profileViewModel by viewModels<ProfileViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun getFragmentView(): Int {
        return R.layout.fragment_profile
    }

    override fun configUi() {
       val userId = tokenManager.getUserID(Constants.USER_ID).toInt()
        profileViewModel.getProfileData(userId)

    }

    override fun setupNavigation() {

    }

    override fun binObserver() {
        profileViewModel.profileDataVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    setProfileData(it.data)
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
    }
    private fun setProfileData(data: ResponsePersonalProfile?) {
        if (data != null) {
            binding.firstName.setText(data.firstName)
            binding.lastName.setText(data.lastName)
            binding.email.setText(data.email)
            var roleList = ""
            data.roles?.forEach { roles ->
                roleList += "${roles},"
            }
            binding.deciseRoleEt.setText(roleList)
            binding.phoneNumberEt.setText(data.phoneNumber)
            binding.jobTitleEt.setText(data.designation)
            binding.departmentEt.setText(data.department)
            var decisionList = ""
            data.decisionGroups?.forEach { decisions ->
                decisionList += "${decisions},"
            }
            binding.decisionGroup.setText(decisionList)
        }
    }


}