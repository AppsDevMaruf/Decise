package com.example.decise.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.databinding.FragmentHomeBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_home
    }

    override fun configUi() {
        profileViewModel.getProfileData(40)

    }

    override fun binObserver() {
        profileViewModel.profileDataVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    getProfileData(it.data)
                }

                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getProfileData(data: ResponsePersonalProfile?) {
        if (data!=null){
            binding.userName.text = "Welcome ${data.firstName} ${data.lastName}"
            binding.userWlcMgs.text = "My name is ${data.firstName} and I am very happy to welcome you on board "
        }

    }


}