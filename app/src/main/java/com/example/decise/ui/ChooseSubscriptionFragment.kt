package com.example.decise.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageSwitcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.models.subscription.subscriptionList.ResponseSubscriptionsList
import com.example.decise.databinding.FragmentChooseSubscriptionBinding
import com.example.decise.utils.DurationType
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseSubscriptionFragment : BaseFragment<FragmentChooseSubscriptionBinding>() {
    private val subscriptionViewModel by activityViewModels<SubscriptionViewModel>()
    override fun getFragmentView(): Int {
        return R.layout.fragment_choose_subscription
    }

    override fun configUi() {
        subscriptionViewModel.getSubscriptionListVM()
    }

    override fun setupNavigation() {
        binding.showHidePersonalBtn.setOnClickListener {
            binding.featuresPersonal.show()
        }
        binding.selectPersonalBtn.setOnClickListener {
            val request = RequestChooseSubscriptionType(
                cardHolderName = null,
                cardNumber = null,
                cvvNumber = null,
                durationType = "MONTHLY",//YEARLY
                expiryDate = null,
                subscriptionPeriodInDays = 30,
                subscriptionType = "PERSONAL"
            )
            subscriptionViewModel.setChooseSubscriptionResponse(request)

            findNavController().navigate(R.id.action_chooseSubscriptionFragment_to_cardInfoFragment)

        }
        binding.subTypeBusinessBtn.setOnClickListener {
            val result = RequestChooseSubscriptionType(
                cardHolderName = null,
                cardNumber = null,
                cvvNumber = null,
                durationType = binding.priceTypeBusiness.text.toString().uppercase(),
                expiryDate = null,
                subscriptionPeriodInDays = 30,
                subscriptionType = "BUSINESS"
            )
            subscriptionViewModel.setChooseSubscriptionResponse(result)
            Log.d("TAG", "BUSINESS: $result")
            findNavController().navigate(R.id.action_chooseSubscriptionFragment_to_cardInfoFragment)
        }
        binding.subTypeEnterpriseBtn.setOnClickListener {
            val result = RequestChooseSubscriptionType(
                cardHolderName = null,
                cardNumber = null,
                cvvNumber = null,
                durationType = binding.priceTypeEnterprise.text.toString().uppercase(),//YEARLY
                expiryDate = null,
                subscriptionPeriodInDays = 30,
                subscriptionType = "ENTERPRISE"
            )
            subscriptionViewModel.setChooseSubscriptionResponse(result)
            Log.d("TAG", "ENTERPRISE: $result")
            findNavController().navigate(R.id.action_chooseSubscriptionFragment_to_cardInfoFragment)
        }
        binding.subTypeEntrepreneurBtn.setOnClickListener {
            val result = RequestChooseSubscriptionType(
                cardHolderName = null,
                cardNumber = null,
                cvvNumber = null,
                durationType = binding.priceTypeEntrepreneur.text.toString().uppercase(),
                expiryDate = null,
                subscriptionPeriodInDays = 30,
                subscriptionType = "ENTREPRENEUR"
            )
            subscriptionViewModel.setChooseSubscriptionResponse(result)
            Log.d("TAG", "ENTREPRENEUR: $result")
            findNavController().navigate(R.id.action_chooseSubscriptionFragment_to_cardInfoFragment)
        }
    }

    override fun binObserver() {
        subscriptionViewModel.getSubscriptionListVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    setSubDetails(it.data)

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

    @SuppressLint("SetTextI18n")
    private fun setSubDetails(data: ResponseSubscriptionsList?) {
        if (data != null) {
            setPersonalData(data)
            setEntrepreneurData(data)
            setBusinessData(data)
            setEnterpriseData(data)
        }
    }

    private fun setPersonalData(data: ResponseSubscriptionsList) {
        val featuresPersonal = data.subscription?.get(0)
        binding.titlePersonal.text = featuresPersonal?.title
        binding.detailsPersonal.text = featuresPersonal?.details
        toggleButton(binding.showHidePersonalBtn, binding.featuresPersonal)
        featuresPersonal?.features?.let { getFeatures(it, binding.featuresPersonal) }
    }

    private fun setEntrepreneurData(data: ResponseSubscriptionsList) {
        val featuresEntrepreneur = data.subscription?.get(1)
        binding.titleEntrepreneur.text = featuresEntrepreneur?.title
        binding.detailsEntrepreneur.text = featuresEntrepreneur?.details
        binding.currencyCodeEntrepreneur.text = featuresEntrepreneur?.currencyCode
        featuresEntrepreneur?.features?.let { getFeatures(it, binding.featuresEntrepreneur) }
        toggleButton(binding.showHideEntrepreneurBtn, binding.featuresEntrepreneur)
        switchButton(
            featuresEntrepreneur,
            binding.switchEntrepreneur,
            binding.priceTypeEntrepreneur,
            binding.priceEntrepreneur
        )


    }

    private fun setBusinessData(data: ResponseSubscriptionsList) {
        val featuresBusiness = data.subscription?.get(2)
        binding.titleBusiness.text = featuresBusiness?.title
        binding.detailsBusiness.text = featuresBusiness?.details
        binding.currencyCodeBusiness.text = featuresBusiness?.currencyCode
        featuresBusiness?.features?.let { getFeatures(it, binding.featuresBusiness) }
        toggleButton(binding.showHideBusinessBtn, binding.featuresBusiness)
        switchButton(
            featuresBusiness,
            binding.switchBusiness,
            binding.priceTypeBusiness,
            binding.priceBusiness
        )
    }

    private fun setEnterpriseData(data: ResponseSubscriptionsList) {
        val featuresEnterprise = data.subscription?.get(3)
        binding.titleEnterprise.text = featuresEnterprise?.title
        binding.detailsEnterprise.text = featuresEnterprise?.details
        binding.currencyCodeEnterprise.text = featuresEnterprise?.currencyCode
        featuresEnterprise?.features?.let { getFeatures(it, binding.featuresEnterprise) }
        toggleButton(binding.showHideEnterpriseBtn, binding.featuresEnterprise)
        switchButton(
            featuresEnterprise,
            binding.switchEnterprise,
            binding.priceTypeEnterprise,
            binding.priceEnterprise
        )
    }

    private fun toggleButton(showHideBtn: TextView, showHideView: TextView) {

        var showHideText = false
        showHideBtn.setOnClickListener {
            showHideText = !showHideText
            if (showHideText) {
                showHideView.show()
                showHideBtn.text = "Hide Details"
                showHideBtn.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_up,
                    0
                )
            } else {
                showHideView.gone()
                showHideBtn.text = "See Details"
                showHideBtn.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_down,
                    0
                )
            }
        }

    }

    private fun getFeatures(
        features: List<ResponseSubscriptionsList.Subscription.Feature?>,
        view: TextView
    ) {
        var strFeatures = ""
        features.forEach { item ->
            strFeatures += "${item?.title}\n"
        }
        view.text = strFeatures
    }

    @SuppressLint("SetTextI18n")
    private fun switchButton(
        data: ResponseSubscriptionsList.Subscription?,
        switchButton: SwitchCompat,
        durationType: TextView,
        price: TextView,
    ) {
        if (switchButton.isChecked) {
            durationType.text = "Yearly"
            price.text = data?.currencySymbol + data?.priceYearly

        } else {
            durationType.text = "Monthly"
            price.text = data?.currencySymbol + data?.priceMonthly


        }
        switchButton.setOnCheckedChangeListener { _, cheeked ->
            if (cheeked) {
                durationType.text = "Yearly"
                price.text = data?.currencySymbol + data?.priceYearly

            } else {
                durationType.text = "Monthly"
                price.text = data?.currencySymbol + data?.priceMonthly
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
