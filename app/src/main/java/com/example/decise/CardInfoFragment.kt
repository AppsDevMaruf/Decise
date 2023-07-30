package com.example.decise

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentCardInfoBinding
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardInfoFragment : BaseFragment<FragmentCardInfoBinding>() {
    private val subscriptionViewModel by activityViewModels<SubscriptionViewModel>()
    override fun getFragmentView(): Int {
        return R.layout.fragment_card_info
    }


    override fun configUi() {
        buttonEnableAfterTextFillUp()
    }

    private fun validateExpiry(expiryDate: String): Boolean {

        return expiryDate.matches(Regex("^(0[1-9]|1[0-2])/(\\d{2})$"))
    }

    private fun formatCardNumber(cardNumber: String): String {
        val formatted = StringBuilder()
        var count = 0

        for (ch in cardNumber) {
            formatted.append(ch)
            count++

            if (count == 4) {
                formatted.append(" ")
                count = 0
            }
        }

        return formatted.toString().trim()
    }

    @SuppressLint("SetTextI18n")
    override fun setupNavigation() {

    }

    @SuppressLint("SetTextI18n")
    private fun buttonEnableAfterTextFillUp() {
        var hasCardNumber = false
        var hasExpiry = false
        var hasCvv = false

        binding.cardNumberEt.addTextChangedListener(object : TextWatcher {
            var isFormatting = false
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) {
                    return
                }
                isFormatting = true
                // Remove previous spaces
                val cardNumber = s?.toString()?.replace(" ", "") ?: ""
                if (!cardNumber.isNullOrBlank()) {
                    // Add spaces after every four digits
                    val formattedCardNumber = formatCardNumber(cardNumber)
                    hasCardNumber = !formattedCardNumber.trim().isNullOrBlank()
                    binding.cardNumberWarning.gone()
                    enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                    // Set the formatted text to the EditText
                    binding.cardNumberEt.setText(formattedCardNumber)
                    // Move the cursor to the end of the text
                    binding.cardNumberEt.setSelection(formattedCardNumber.length)
                    if (formattedCardNumber.length == 19) {
                        hasCardNumber = true
                        binding.cardNumberWarning.gone()
                        enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                    } else {
                        hasCardNumber = false
                        binding.cardNumberWarning.text = "Invalid card number"
                        binding.cardNumberWarning.show()
                        enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                    }
                } else {
                    hasCardNumber = false
                    binding.cardNumberWarning.text = "This field is required"
                    binding.cardNumberWarning.show()
                    enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                }
                isFormatting = false
            }
        })

        binding.expiryEt.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) {
                    return
                }
                isFormatting = true
                // Add "/" after the first two digits
                if (s?.length == 2 && !s.toString().endsWith("/")) {
                    val month = s.toString()
                    s.replace(0, 2, "$month/")
                }
                // Perform validation on the entered text
                val expiryDate = s?.toString() ?: ""
                if (!expiryDate.isNullOrBlank()) {
                    hasExpiry = !expiryDate.trim().isNullOrBlank()
                    val isValidExpiry = validateExpiry(expiryDate)
                    if (!isValidExpiry) {
                        binding.expiryWarning.text = "Invalid expiry date"
                        binding.expiryWarning.show()
                        hasExpiry = false
                        enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                    } else {
                        hasExpiry = true
                        enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                        binding.expiryWarning.gone()
                    }

                    isFormatting = false
                } else {
                    hasExpiry = false
                    binding.expiryWarning.text = "This field is required"
                    binding.expiryWarning.show()
                    enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                }
            }

        })

        binding.cvvEt.onTextChanged {

            if (!it.trim().isNullOrBlank()) {
                if (it.length == 3 || it.length == 4) {
                    hasCvv = !it.trim().isNullOrBlank()
                    binding.cvvWarning.gone()
                    enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                } else {
                    hasCvv = false
                    binding.cvvWarning.text = "Valid example: 774"
                    binding.cvvWarning.show()
                    enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
                }

            } else {
                hasCvv = false
                binding.cvvWarning.text = "This field is required"
                binding.cvvWarning.show()
                enableBtn(hasCardNumber && hasExpiry && hasCvv, binding.continueBtn)
            }
        }

    }


    override fun binObserver() {

        subscriptionViewModel.chooseSubscriptionResponse.observe(this) {
            Log.d("TAG", "chooseSubscriptionResponse: $it")
        }
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




