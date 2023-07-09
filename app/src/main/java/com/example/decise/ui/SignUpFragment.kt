package com.example.decise.ui

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentSignUpBinding
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
import com.example.decise.utils.toast

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    var hasFirstName = false
    var hasLastName = false
    var hasPassword = false
    var hasConfirmPassword = false

    override fun getFragmentView(): Int {
        return R.layout.fragment_sign_up
    }

    override fun configUi() {
        binding.toolbarSignUp.toolbarTitle.text = "Have an account?"
        binding.toolbarSignUp.button.text = "Log In"
        buttonEnableAfterTextFillUp()

    }

    override fun setupNavigation() {
        binding.toolbarSignUp.button.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }
        binding.signUpCompleteBtn.setOnClickListener {
            if (isPasswordMatch(
                    binding.password.text.toString().trim(),
                    binding.confirmPassword.text.toString().trim()
                )
            ) {

            } else {
                toast("Password not match")
            }


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
            if (!it.trim().isNullOrBlank()) {
                binding.passwordWarning.gone()
                hasPassword = !it.trim().isNullOrBlank()
                validatePassWord(it.trim())
                if (isPasswordMatch(
                        binding.password.text.toString().trim(),
                        binding.confirmPassword.text.toString().trim()
                    )
                ) {
                    enableBtn(
                        hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                        binding.signUpCompleteBtn
                    )
                } else {
                    hasPassword = false
                    enableBtn(
                        hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                        binding.signUpCompleteBtn
                    )
                }

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
            if (!it.trim().isNullOrBlank()) {
                binding.confirmPasswordWarning.gone()
                hasConfirmPassword = !it.trim().isNullOrBlank()
                if (isPasswordMatch(
                        binding.password.text.toString().trim(),
                        binding.confirmPassword.text.toString().trim()
                    )
                ) {
                    enableBtn(
                        hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                        binding.signUpCompleteBtn
                    )
                } else {
                    hasConfirmPassword = false
                    enableBtn(
                        hasFirstName && hasLastName && hasPassword && hasConfirmPassword,
                        binding.signUpCompleteBtn
                    )
                }
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

    private fun validatePassWord(password: String) {

        val upperCase: Boolean = hasUpperCase(password)
        val lowerCase: Boolean = hasLowerCase(password)
        val hasDigit: Boolean = hasDigit(password)
        val isLength: Boolean = isLength8(password)
        val hasSpecialChar: Boolean = hasSpecailChar(password)


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




        if (hasDigit && lowerCase && upperCase && isLength && hasSpecialChar) {
            enableBtn(true,binding.signUpCompleteBtn)


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