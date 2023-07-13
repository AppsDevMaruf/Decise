package com.example.decise

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentChooseSubscriptionBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.isValidEmail
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.utils.toast
import com.example.decise.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseSubscriptionFragment : BaseFragment<FragmentChooseSubscriptionBinding>() {
    private val subscriptionViewModel by viewModels<SubscriptionViewModel>()
    override fun getFragmentView(): Int {
        return R.layout.fragment_choose_subscription
    }
    override fun configUi() {
        subscriptionViewModel.getSubscriptionListVM()

    }


    override fun setupNavigation() {

    }


    override fun binObserver() {
        subscriptionViewModel.getSubscriptionListVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    //token
                    Log.i("TAG", "binObserver: ${it.data?.total}")
                }

                is NetworkResult.Error -> {
                    showDialog(
                        context = requireActivity(),
                        title = "",
                        details = "${it.message}",
                        resId = R.drawable.ic_round_warning,
                        yesContent = "Okay",
                        noContent = "Cancel",
                        showNoBtn = false,
                        positiveFun = {
                        }, {}
                    )
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }



}