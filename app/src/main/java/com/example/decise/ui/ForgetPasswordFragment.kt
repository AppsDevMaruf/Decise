package com.example.decise.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentForgetPasswordBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.isValidEmail
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.utils.toast
import com.example.decise.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_forget_password
    }

    override fun configUi() {
        buttonEnableAfterTextFillUp()

    }

    @SuppressLint("SetTextI18n")
    override fun setupNavigation() {
        binding.forgetPasswordBtn.setOnClickListener {

            if (!isValidEmail(binding.email.text.toString().trim())) {
                binding.emailWarning.show()
                binding.emailWarning.text = "Enter a valid email"

            } else {
                val email = binding.email.text.toString().trim()
                authViewModel.forgetPasswordVM(email)

            }
        }

    }

    private fun buttonEnableAfterTextFillUp() {
        var emailGiven = false
        binding.email.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                emailGiven = !it.trim().isNullOrBlank()
                binding.emailWarning.gone()
                enableBtn(emailGiven, binding.forgetPasswordBtn)
            } else {
                emailGiven = false
                binding.emailWarning.show()
                enableBtn(emailGiven, binding.forgetPasswordBtn)
            }
        }


    }

    override fun binObserver() {
        authViewModel.responseForgetPassword.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    //token
                    Log.e("TAG", "binObserver: ${it.data}")
                    toast("Send Email  Success")


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