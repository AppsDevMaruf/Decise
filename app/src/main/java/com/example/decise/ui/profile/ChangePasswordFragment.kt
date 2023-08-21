package com.example.decise.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.profile.changePassword.RequestChangePassword
import com.example.decise.databinding.FragmentChangePasswordBinding
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
import com.example.decise.utils.showErrorDialog
import com.example.decise.utils.toast
import com.example.decise.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_change_password
    }

    override fun configUi() {
        buttonEnableAfterTextFillUp()
        binding.submitBtn.setOnClickListener {
            profileViewModel.changePasswordVM(
                RequestChangePassword(
                    oldPassword = binding.currentPassword.text.toString().trim(),
                    newPassword = binding.newPassword.text.toString().trim(),
                )
            )
        }
    }

    override fun binObserver() {
        profileViewModel.responseChangePasswordVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                    toast("Password Change Successfully")
                }

                is NetworkResult.Error -> {
                    it.message?.let { errorMgs -> showErrorDialog(errorMgs) {} }

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
    }

    private fun buttonEnableAfterTextFillUp() {

        var hasCurrentPassword = false
        var hasNewPassword = false
        var hasConfirmPassword = false


        binding.currentPassword.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasCurrentPassword = !it.trim().isNullOrBlank()
                binding.currentPasswordWarning.gone()
                enableBtn(
                    hasCurrentPassword && hasNewPassword && hasConfirmPassword, binding.submitBtn
                )
            } else {
                hasCurrentPassword = false
                binding.currentPasswordWarning.show()
                enableBtn(
                    hasCurrentPassword && hasNewPassword && hasConfirmPassword, binding.submitBtn
                )
            }
        }

        binding.newPassword.onTextChanged {

            val confirmPassword = binding.confirmPassword.text.toString()
            if (!it.trim().isNullOrBlank()) {
                binding.passwordWarning.gone()
                hasNewPassword = !it.trim().isNullOrBlank()
                validatePassWord(it.trim(), confirmPassword)

            } else {
                hasNewPassword = false
                binding.passwordWarning.show()
                enableBtn(
                    hasCurrentPassword && hasNewPassword && hasConfirmPassword, binding.submitBtn
                )
            }
        }
        binding.confirmPassword.onTextChanged {
            val password = binding.newPassword.text.toString()

            if (!it.trim().isNullOrBlank()) {
                binding.confirmPasswordWarning.gone()
                hasConfirmPassword = !it.trim().isNullOrBlank()
                validatePassWord(password, it.trim())

            } else {
                hasConfirmPassword = false
                binding.confirmPasswordWarning.show()
                enableBtn(
                    hasCurrentPassword && hasNewPassword && hasConfirmPassword,
                    binding.submitBtn
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
            enableBtn(true, binding.submitBtn)

        } else {
            enableBtn(false, binding.submitBtn)
        }


    }


}