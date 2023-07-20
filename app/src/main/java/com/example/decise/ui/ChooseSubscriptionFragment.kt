package com.example.decise.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.models.subscription.subscriptionList.ResponseSubscriptionsList
import com.example.decise.databinding.FragmentChooseSubscriptionBinding
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.utils.showDialog
import com.example.decise.viewmodel.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseSubscriptionFragment : BaseFragment<FragmentChooseSubscriptionBinding>() {
    private val subscriptionViewModel by viewModels<SubscriptionViewModel>()
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
            subscriptionViewModel.setChooseSubscriptionResponse(
                RequestChooseSubscriptionType(
                    cardHolderName = null,
                    cardNumber = null,
                    cvvNumber = null,
                    durationType = "MONTHLY",//YEARLY
                    expiryDate = null,
                    subscriptionPeriodInDays = 30,
                    subscriptionType = null
                )

            )
            findNavController().navigate(R.id.action_chooseSubscriptionFragment_to_cardInfoFragment)

        }
        binding.subTypeBusinessBtn.setOnClickListener {
            subscriptionViewModel.setChooseSubscriptionResponse(
                RequestChooseSubscriptionType(
                    cardHolderName = null,
                    cardNumber = null,
                    cvvNumber = null,
                    durationType = "MONTHLY",//YEARLY
                    expiryDate = null,
                    subscriptionPeriodInDays = 30,
                    subscriptionType = "BUSINESS"
                )

            )
            findNavController().navigate(R.id.action_chooseSubscriptionFragment_to_cardInfoFragment)

        }
        binding.subTypeEnterpriseBtn.setOnClickListener {
            subscriptionViewModel.setChooseSubscriptionResponse(
                RequestChooseSubscriptionType(
                    cardHolderName = null,
                    cardNumber = null,
                    cvvNumber = null,
                    durationType = "MONTHLY",//YEARLY
                    expiryDate = null,
                    subscriptionPeriodInDays = 30,
                    subscriptionType = "ENTERPRISE"
                )

            )
            findNavController().navigate(R.id.action_chooseSubscriptionFragment_to_cardInfoFragment)

        }
        binding.subTypeEntrepreneurBtn.setOnClickListener {
            subscriptionViewModel.setChooseSubscriptionResponse(
                RequestChooseSubscriptionType(
                    cardHolderName = null,
                    cardNumber = null,
                    cvvNumber = null,
                    durationType = "MONTHLY",//YEARLY
                    expiryDate = null,
                    subscriptionPeriodInDays = 30,
                    subscriptionType = "ENTREPRENEUR"
                )

            )
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
            val featuresPersonal = data.subscription?.get(0)
            binding.titlePersonal.text = data.subscription?.get(0)?.title
            binding.detailsPersonal.text = data.subscription?.get(0)?.details
            binding.featuresPersonal.text = "${featuresPersonal?.features?.get(0)?.title}\n" +
                    "${featuresPersonal?.features?.get(1)?.title}\n" +
                    "${featuresPersonal?.features?.get(2)?.title}\n" +
                    "${featuresPersonal?.features?.get(3)?.title}\n" +
                    "${featuresPersonal?.features?.get(4)?.title}\n" +
                    "${featuresPersonal?.features?.get(5)?.title}\n" +
                    "${featuresPersonal?.features?.get(6)?.title}\n"

            val featuresEntrepreneur = data.subscription?.get(1)
            binding.titleEntrepreneur.text = data.subscription?.get(1)?.title
            binding.detailsEntrepreneur.text = data.subscription?.get(1)?.details
            binding.featuresEntrepreneur.text =
                "${featuresEntrepreneur?.features?.get(0)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(1)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(2)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(3)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(4)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(5)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(6)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(7)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(8)?.title}\n" +
                        "${featuresEntrepreneur?.features?.get(9)?.title}\n"

            val featuresBusiness = data.subscription?.get(2)
            binding.titleBusiness.text = data.subscription?.get(2)?.title
            binding.detailsBusiness.text = data.subscription?.get(2)?.details
            binding.featuresBusiness.text = "${featuresBusiness?.features?.get(0)?.title}\n" +
                    "${featuresBusiness?.features?.get(1)?.title}\n" +
                    "${featuresBusiness?.features?.get(2)?.title}\n" +
                    "${featuresBusiness?.features?.get(3)?.title}\n" +
                    "${featuresBusiness?.features?.get(4)?.title}\n" +
                    "${featuresBusiness?.features?.get(5)?.title}\n" +
                    "${featuresBusiness?.features?.get(6)?.title}\n" +
                    "${featuresBusiness?.features?.get(7)?.title}\n" +
                    "${featuresBusiness?.features?.get(8)?.title}\n" +
                    "${featuresBusiness?.features?.get(9)?.title}\n"

            val featuresEnterprise = data.subscription?.get(3)
            binding.titleEnterprise.text = data.subscription?.get(3)?.title
            binding.detailsEnterprise.text = data.subscription?.get(3)?.details
            binding.featuresEnterprise.text = "${featuresEnterprise?.features?.get(0)?.title}\n" +
                    "${featuresEnterprise?.features?.get(1)?.title}\n" +
                    "${featuresEnterprise?.features?.get(2)?.title}\n" +
                    "${featuresEnterprise?.features?.get(3)?.title}\n" +
                    "${featuresEnterprise?.features?.get(4)?.title}\n" +
                    "${featuresEnterprise?.features?.get(5)?.title}\n" +
                    "${featuresEnterprise?.features?.get(6)?.title}\n" +
                    "${featuresEnterprise?.features?.get(7)?.title}\n" +
                    "${featuresEnterprise?.features?.get(8)?.title}\n" +
                    "${featuresEnterprise?.features?.get(9)?.title}\n" +
                    "${featuresEnterprise?.features?.get(10)?.title}\n" +
                    "${featuresEnterprise?.features?.get(11)?.title}\n" +
                    "${featuresEnterprise?.features?.get(12)?.title}\n"

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