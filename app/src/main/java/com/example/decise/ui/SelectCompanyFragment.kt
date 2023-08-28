package com.example.decise.ui

import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.networks.auth.login.ResponseLogin
import com.example.decise.data.prefs.PrefKeys
import com.example.decise.data.prefs.PreferenceManager
import com.example.decise.databinding.FragmentSelectCompanyBinding
import com.example.decise.utils.hideActionBar
import com.example.decise.utils.showActionBar
import com.example.decise.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectCompanyFragment : BaseFragment<FragmentSelectCompanyBinding>() {
    private val authViewModel by activityViewModels<AuthViewModel>()
    private var otherCompanies = mutableListOf<ResponseLogin.CompanyDto>()

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun getFragmentView(): Int {
        return R.layout.fragment_select_company
    }

    override fun configUi() {
        // Populate the "Others Company" section
        val othersCompanyLayout = binding.othersCompanyListLayout
        // Clear existing views before adding new ones
        othersCompanyLayout.removeAllViews()
        for (companyName in otherCompanies) {
            val companyButton = Button(requireContext())
            companyButton.text = companyName.name
            companyButton.setBackgroundResource(R.color.bg_item_dashboard)
            companyButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            companyButton.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // Set individual margins for the button
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 8, 0, 8) // Replace with your desired margins
            companyButton.layoutParams = layoutParams

            companyButton.setOnClickListener {
                // Handle the company button click event
                onCompanyButtonClicked(companyName)
            }
            othersCompanyLayout.addView(companyButton)
        }
    }

    override fun binObserver() {
        authViewModel.loginVMLD.observe(viewLifecycleOwner) { it ->
            if (it.data != null) {

                it.data.companyId?.let { companyId ->
                    onMyCompanyButtonClicked(companyId)
                }
                binding.myCompany.text = it.data.companyName
                otherCompanies = it.data.companyDtoList as MutableList<ResponseLogin.CompanyDto>
                configUi()

            }

        }
    }


    private fun onCompanyButtonClicked(companyName: ResponseLogin.CompanyDto) {
        companyName.id?.let { preferenceManager.put(PrefKeys.SELECTED_COMPANY_ID, it) }
        findNavController().navigate(R.id.action_selectCompanyFragment_to_homeFragment)
    }


    private fun onMyCompanyButtonClicked(companyId: Int) {
        binding.myCompany.setOnClickListener {
            preferenceManager.put(PrefKeys.SELECTED_COMPANY_ID, companyId)

            findNavController().navigate(R.id.action_selectCompanyFragment_to_homeFragment)
        }


    }

    override fun onResume() {
        super.onResume()
        hideActionBar()
    }

    override fun onStop() {
        super.onStop()
        showActionBar()
    }

}