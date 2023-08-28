package com.example.decise.ui

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.networks.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.data.prefs.PrefKeys
import com.example.decise.data.prefs.PreferenceManager
import com.example.decise.databinding.FragmentHomeBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.utils.showErrorDialog
import com.example.decise.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val profileViewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun getFragmentView(): Int {
        return R.layout.fragment_home
    }

    override fun configUi() {
        val userID: Int = preferenceManager.get(PrefKeys.SAVED_USER_ID) as Int
        profileViewModel.getProfileData(userID)

    }

    override fun binObserver() {
        profileViewModel.profileDataVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    getProfileData(it.data)
                }

                is NetworkResult.Error -> {
                    it.message?.let { it1 -> showErrorDialog(it1) {} }
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getProfileData(data: ResponsePersonalProfile?) {
        if (data != null) {
            binding.userName.text = "Welcome ${data.firstName} ${data.lastName}"
            binding.userWlcMgs.text =
                "My name is ${data.firstName} and I am very happy to welcome you on board "
        }

    }


}