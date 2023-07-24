package com.example.decise

import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentCardInfoBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.utils.toast
import com.example.decise.viewmodel.AuthViewModel
import com.example.decise.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardInfoFragment : BaseFragment<FragmentCardInfoBinding>() {
    private val subscriptionViewModel by viewModels<SubscriptionViewModel>()

    override fun getFragmentView(): Int {
        return R.layout.fragment_card_info
    }

    override fun configUi() {
        subscriptionViewModel.chooseSubscriptionResponse.observe(viewLifecycleOwner){
            Log.d("TAG", "chooseSubscriptionResponse: $it")

        }
        buttonEnableAfterTextFillUp()

    }

    @SuppressLint("SetTextI18n")
    override fun setupNavigation() {

    }

    private fun buttonEnableAfterTextFillUp() {
        var hasCardNumber = false
        var hasExpiry = false
        var hasCvv = false

        binding.cardNumberEt.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasCardNumber = !it.trim().isNullOrBlank()
                binding.cardNumberWarning.gone()

                enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
            } else {
                hasCardNumber = false
                binding.cardNumberWarning.show()
                enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
            }
        }


        binding.expiryEt.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasExpiry = !it.trim().isNullOrBlank()
                binding.expiryWarning.gone()
                enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
            } else {
                hasExpiry = false
                binding.expiryWarning.show()
                enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
            }
        }
        binding.cvvEt.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasCvv = !it.trim().isNullOrBlank()
                binding.cvvWarning.gone()
                enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
            } else {
                hasCvv = false
                binding.cvvWarning.show()
                enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
            }
        }

    }


    override fun binObserver() {


     /*   subscriptionViewModel.responseSendEmail.observe(this) {
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
        }*/


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




