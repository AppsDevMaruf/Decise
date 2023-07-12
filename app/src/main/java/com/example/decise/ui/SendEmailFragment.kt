package com.example.decise.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentSendEmailBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.isValidEmail
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.toast
import com.example.decise.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SendEmailFragment : BaseFragment<FragmentSendEmailBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_send_email
    }

    override fun configUi() {
        buttonEnableAfterTextFillUp()

    }

    @SuppressLint("SetTextI18n")
    override fun setupNavigation() {
        binding.verifyBtn.setOnClickListener {

            if (!isValidEmail(binding.email.text.toString().trim())) {
                binding.emailWarning.show()
                binding.emailWarning.text = "Enter a valid email"

            } else {
                val email = binding.email.text.toString().trim()

                lifecycleScope.launch {
                    authViewModel.sendEmailVM(email)
                }
            }
        }
        binding.toolbarLogin.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.logInFragment)
        }
    }

    private fun buttonEnableAfterTextFillUp() {
        var emailGiven = false
        binding.email.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                emailGiven = !it.trim().isNullOrBlank()
                binding.emailWarning.gone()
                enableBtn(emailGiven, binding.verifyBtn)
            } else {
                emailGiven = false
                binding.emailWarning.show()
                enableBtn(emailGiven, binding.verifyBtn)
            }
        }


    }

    override fun binObserver() {
        authViewModel.sendEmailVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    //token
                    Log.e("TAG", "binObserver: ${it.data}")
                    toast("Send Email  Success")


                }

                is NetworkResult.Error -> {
                    Log.e("TAG", "Error: ${it.message}")
                    toast("${it.message}")
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