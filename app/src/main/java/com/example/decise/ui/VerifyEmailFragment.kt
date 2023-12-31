package com.example.decise.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.networks.auth.verifyEmail.ResponseVerifyEmail
import com.example.decise.databinding.FragmentVerifyEmailBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.utils.toast
import com.example.decise.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyEmailFragment : BaseFragment<FragmentVerifyEmailBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()
    private val arg: VerifyEmailFragmentArgs by navArgs()
    private var subscriptionType = ""
    private var signupType = ""
    private var email = ""

    override fun getFragmentView(): Int {
        return R.layout.fragment_verify_email
    }

    override fun configUi() {
        subscriptionType = arg.subscriptionType
        signupType = arg.signupType
        email = arg.email
        val evc = arg.evc

        authViewModel.verifyEmailVM(subscriptionType, signupType, email, evc)

    }

    override fun binObserver() {
        authViewModel.verifyEmailVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    toast("verifyEmail Successful")
                    val responseVerifyEmail = ResponseVerifyEmail(
                        subscriptionType = subscriptionType,
                        signupType = signupType,
                        email = email,
                    )

                    val action =
                        VerifyEmailFragmentDirections.actionVerifyEmailFragmentToSignUpFragment(
                            responseVerifyEmail
                        )
                    findNavController().navigate(action)

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