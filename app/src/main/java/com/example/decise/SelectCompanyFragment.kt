package com.example.decise

import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentSelectCompanyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCompanyFragment : BaseFragment<FragmentSelectCompanyBinding>() {
    private val otherCompanies: List<String> = listOf("Company A", "Company B", "Company C")
    override fun getFragmentView(): Int {
        return R.layout.fragment_select_company
    }

    override fun configUi() {

        // Populate the "Others Company" section

        val othersCompanyLayout = binding.othersCompanyListLayout
        for (companyName in otherCompanies) {
            val companyButton = Button(requireContext())
            companyButton.text = companyName
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

    private fun onCompanyButtonClicked(companyName: String) {
        Log.d("Click", "Company button clicked: $companyName")

    }


}