package com.example.decise.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.auth.login.RequestLogin
import com.example.decise.databinding.FragmentLogInBinding
import com.example.decise.utils.Constants
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.TokenManager
import com.example.decise.utils.gone
import com.example.decise.utils.isConnectedToNetwork
import com.example.decise.utils.isValidEmail
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.utils.showErrorDialog
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
        binding.toolbarLogin.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_sendEmailFragment)
        }
        binding.forgetPasswordBtn.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgetPasswordFragment)
        }
        binding.loginBtn.setOnClickListener {


            if (!isValidEmail(binding.email.text.toString().trim())) {
                binding.emailWarning.show()
            }
            if (binding.password.text.toString().trim() == "") {
                binding.passwordWarning.show()

            } else {
                if (isConnectedToNetwork(requireActivity())) {
                    val email = binding.email.text.toString().trim()
                    val password = binding.password.text.toString().trim()
                    val loginRequestLogin = RequestLogin(email, password)
                    lifecycleScope.launch {
                        authViewModel.loginUserVM(loginRequestLogin)
                    }
                } else {
                    showNoInternetDialog()
                }


            }


        }
    }

    override fun binObserver() {
        authViewModel.loginVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {

                    tokenManager.saveToken(Constants.TOKEN, "${it.data?.type} ${it.data?.token}")
                    tokenManager.saveUserID(Constants.USER_ID, "${it.data?.id}")
                    if (it.data?.firstLogin == true) {
                        findNavController().navigate(R.id.action_logInFragment_to_chooseSubscriptionFragment)

                    } else {
                        findNavController().navigate(R.id.action_logInFragment_to_selectCompanyFragment)
                    }
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
                passwordGiven = !it.trim().isNullOrBlank()
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

    private fun showNoInternetDialog() {
        showDialog(
            context = requireActivity(),
            title = getString(R.string.no_internet_connection),
            details = getString(R.string.no_internet_msg),
            resId = R.drawable.ic_round_warning,
            yesContent = getString(R.string.okay),
            noContent = getString(R.string.cancel),
            showNoBtn = false,
            positiveFun = {},
            negativeFun = {}
        )
    }
}