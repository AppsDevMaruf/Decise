package com.example.decise.ui

import android.content.Intent
import android.media.session.MediaSession.Token
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.login.RequestLogin
import com.example.decise.databinding.FragmentLogInBinding
import com.example.decise.utils.Constants
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.TokenManager
import com.example.decise.utils.gone
import com.example.decise.utils.isValidEmail
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.toast
import com.example.decise.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun getFragmentView(): Int {
        return R.layout.fragment_log_in
    }

    override fun configUi() {
        buttonEnableAfterTextFillUp()
    }

    override fun setupNavigation() {
        binding.loginBtn.setOnClickListener {


            if (!isValidEmail(binding.email.text.toString().trim())) {
                binding.emailWarning.show()

            }
            if (binding.password.text.toString().trim() == "") {
                binding.passwordWarning.show()

            } else {

                val email = binding.email.text.toString().trim()
                val password = binding.password.text.toString().trim()


                val loginRequestLogin = RequestLogin(email, password)

                lifecycleScope.launch {
                    authViewModel.loginUserVM(loginRequestLogin)
                }

            }


        }
    }

    override fun binObserver() {
        authViewModel.loginVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    //token
                    Log.e("TAG", "binObserver: ${it.data}")
                    toast("Login Success")

                    tokenManager.saveToken(Constants.TOKEN, "${it.data?.type} ${it.data?.token}")


                }

                is NetworkResult.Error -> {
                    Log.e("TAG", "binObserver: ${it.data}")
                    toast("${it.message}")
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }


    }

    private fun buttonEnableAfterTextFillUp() {

        var emailGiven = false
        var passwordGiven = false

        binding.email.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                emailGiven = !it.trim().isNullOrBlank()
                binding.emailWarning.gone()
                enableBtn(emailGiven, passwordGiven)
            } else {
                emailGiven = false
                binding.emailWarning.show()
                enableBtn(emailGiven, passwordGiven)
            }
        }

        binding.password.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                binding.passwordWarning.gone()
                passwordGiven =!it.trim().isNullOrBlank()
                enableBtn(emailGiven, passwordGiven)
            } else {
                passwordGiven = false
                binding.passwordWarning.show()
                enableBtn(emailGiven, passwordGiven)
            }


        }

    }

    private fun enableBtn(emailGiven: Boolean, passwordGiven: Boolean) {

        val result = emailGiven && passwordGiven

        if (result) {
            binding.loginBtn.isEnabled
            binding.loginBtn.background =
                AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.button_login_bg_orange_enable
                )

        } else {
            binding.loginBtn.background =
                AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.button_primary_bg_orange_disable
                )

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