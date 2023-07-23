package com.example.decise.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.auth.signUp.RequestSignUp
import com.example.decise.databinding.FragmentSignUpBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.hasDigit
import com.example.decise.utils.hasLowerCase
import com.example.decise.utils.hasSpecailChar
import com.example.decise.utils.hasUpperCase
import com.example.decise.utils.isLength8
import com.example.decise.utils.isPasswordMatch
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()
    private val arg: SignUpFragmentArgs by navArgs()


    override fun getFragmentView(): Int {
        return R.layout.fragment_sign_up
    }

    override fun configUi() {
        val responseVerifyEmail = arg.response
        val email = responseVerifyEmail.email
        val subscriptionType = responseVerifyEmail.subscriptionType
        val signupType: String? = responseVerifyEmail.signupType

        binding.signUpCompleteBtn.setOnClickListener {
            val firstName = binding.firstName.text.trim().toString()
            val lastName = binding.lastName.text.trim().toString()
            val password = binding.password.text?.trim().toString()
            val request = RequestSignUp(
                email = email,
                firstName = firstName,
                lastName = lastName,
                password = password,
                subscriptionType = subscriptionType,
                signUpType = signupType
            )

            authViewModel.completeSignUpVM(request)


        }
        buttonEnableAfterTextFillUp()

    }


    override fun setupNavigation() {
        binding.toolbarSignUp.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.logInFragment)
        }


    }

    private fun buttonEnableAfterTextFillUp() {
        var hasFirstName = false
        var hasLastName = false
        var hasPassword = false
        var hasConfirmPassword = false

        binding.firstName.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasFirstName = !it.trim().isNullOrBlank()
                binding.firstNameWarning.gone()

                enableBtn(
                    hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                    binding.signUpCompleteBtn
                )
            } else {
                hasFirstName = false
                binding.firstNameWarning.show()
                enableBtn(
                    hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                    binding.signUpCompleteBtn
                )
            }
        }
        binding.lastName.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasLastName = !it.trim().isNullOrBlank()
                binding.lastNameWarning.gone()
                enableBtn(
                    hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                    binding.signUpCompleteBtn
                )
            } else {
                hasLastName = false
                binding.lastNameWarning.show()
                enableBtn(
                    hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                    binding.signUpCompleteBtn
                )
            }
        }

        binding.password.onTextChanged {

            val confirmPassword = binding.confirmPassword.text.toString()
            if (!it.trim().isNullOrBlank()) {
                binding.passwordWarning.gone()
                hasPassword = !it.trim().isNullOrBlank()
                validatePassWord(it.trim(), confirmPassword)

            } else {
                hasPassword = false
                binding.passwordWarning.show()
                enableBtn(
                    hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                    binding.signUpCompleteBtn
                )
            }
        }
        binding.confirmPassword.onTextChanged {
            val password = binding.password.text.toString()

            if (!it.trim().isNullOrBlank()) {
                binding.confirmPasswordWarning.gone()
                hasConfirmPassword = !it.trim().isNullOrBlank()
                validatePassWord(password, it.trim())

            } else {
                hasConfirmPassword = false
                binding.confirmPasswordWarning.show()
                enableBtn(
                    hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                    binding.signUpCompleteBtn
                )
            }
        }

    }

    private fun validatePassWord(password: String, confirmPassword: String) {

        val upperCase: Boolean = hasUpperCase(password)
        val lowerCase: Boolean = hasLowerCase(password)
        val hasDigit: Boolean = hasDigit(password)
        val isLength: Boolean = isLength8(password)
        val hasSpecialChar: Boolean = hasSpecailChar(password)
        val hasPasswordMatch: Boolean = isPasswordMatch(password, confirmPassword)


        if (upperCase) {
            binding.oneUpperCaseText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_check, 0, 0, 0
            )
            binding.oneUpperCaseText.setTextColor(resources.getColor(R.color.orange_light))

        } else {
            binding.oneUpperCaseText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.oneUpperCaseText.setTextColor(resources.getColor(R.color.gray))
        }

        if (lowerCase) {
            binding.oneLowerCaseText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_check, 0, 0, 0
            )
            binding.oneLowerCaseText.setTextColor(resources.getColor(R.color.orange_light))

        } else {
            binding.oneLowerCaseText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.oneLowerCaseText.setTextColor(resources.getColor(R.color.gray))
        }

        if (hasDigit) {
            binding.oneNumberText.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_check, 0, 0, 0
            )
            binding.oneNumberText.setTextColor(resources.getColor(R.color.orange_light))

        } else {
            binding.oneNumberText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.oneNumberText.setTextColor(resources.getColor(R.color.gray))
        }

        if (isLength) {
            binding.eightNumberTextLength.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_check, 0, 0, 0
            )
            binding.eightNumberTextLength.setTextColor(resources.getColor(R.color.orange_light))

        } else {
            binding.eightNumberTextLength.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.eightNumberTextLength.setTextColor(resources.getColor(R.color.gray))
        }


        if (hasSpecialChar) {
            binding.specialCharacters.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_check, 0, 0, 0
            )
            binding.specialCharacters.setTextColor(resources.getColor(R.color.orange_light))

        } else {
            binding.specialCharacters.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.specialCharacters.setTextColor(resources.getColor(R.color.gray))
        }
        if (hasPasswordMatch) {
            binding.passwordMatching.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_check, 0, 0, 0
            )
            binding.passwordMatching.setTextColor(resources.getColor(R.color.orange_light))

        } else {
            binding.passwordMatching.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.passwordMatching.setTextColor(resources.getColor(R.color.gray))
        }




        if (hasDigit && lowerCase && upperCase && isLength && hasSpecialChar && hasPasswordMatch) {
            enableBtn(true, binding.signUpCompleteBtn)

        } else {
            enableBtn(false, binding.signUpCompleteBtn)
        }


    }

    override fun binObserver() {
        authViewModel.completeSignUpVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)

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