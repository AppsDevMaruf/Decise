package com.example.decise.ui.profile

import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.profile.DropDownModel
import com.example.decise.data.models.profile.decisionGroups.DecisionGroups
import com.example.decise.data.models.profile.departments.Departments
import com.example.decise.data.models.profile.designations.Designations
import com.example.decise.data.models.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.data.models.profile.update_personal_profile.RequestUpdatePersonalProfile
import com.example.decise.databinding.FragmentProfileBinding
import com.example.decise.interfaces.DropDownInteractionListener
import com.example.decise.ui.profile.adapter.DropDownAdapter
import com.example.decise.utils.Constants
import com.example.decise.utils.DropDownType
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.TokenManager
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.hideSoftKeyboard
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.utils.toast
import com.example.decise.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), DropDownInteractionListener {

    private lateinit var dropDownAdapter: DropDownAdapter
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var departmentList: ArrayList<Departments>
    private lateinit var jobTitleList: ArrayList<Designations>
    private lateinit var decisionGroupList: ArrayList<DecisionGroups>

    @Inject
    lateinit var tokenManager: TokenManager
    override fun getFragmentView(): Int {
        return R.layout.fragment_profile
    }

    override fun configUi() {
        val userId = tokenManager.getUserID(Constants.USER_ID).toInt()
        profileViewModel.getProfileData(userId)

        buttonEnableAfterTextFillUp()
    }

    private fun buttonEnableAfterTextFillUp() {
        var hasFirstName = false
        var hasLastName = false
        var hasPhoneNumber = false


        binding.firstName.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasFirstName = !it.trim().isNullOrBlank()
                binding.firstNameWarning.gone()
                enableBtn(hasFirstName && hasLastName && hasPhoneNumber, binding.updateBtn)
            } else {
                hasFirstName = false
                binding.firstNameWarning.show()
                enableBtn(hasFirstName && hasLastName && hasPhoneNumber, binding.updateBtn)
            }
        }
        binding.lastName.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasLastName = !it.trim().isNullOrBlank()
                binding.lastNameWarning.gone()
                enableBtn(hasFirstName && hasLastName && hasPhoneNumber, binding.updateBtn)
            } else {
                hasLastName = false
                binding.lastNameWarning.show()
                enableBtn(hasFirstName && hasLastName && hasPhoneNumber, binding.updateBtn)
            }
        }
        binding.phoneNumberEt.onTextChanged {
            if (!it.trim().isNullOrBlank()) {
                hasPhoneNumber = !it.trim().isNullOrBlank()
                binding.phoneNumberWarning.gone()
                enableBtn(hasFirstName && hasLastName && hasPhoneNumber, binding.updateBtn)
            } else {
                hasPhoneNumber = false
                binding.phoneNumberWarning.show()
                enableBtn(hasFirstName && hasLastName && hasPhoneNumber, binding.updateBtn)
            }
        }


    }

    override fun setupNavigation() {


    }

    override fun binObserver() {
        profileViewModel.profileDataVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.companyId?.let { companyId ->
                        profileViewModel.getDesignations(companyId)
                        profileViewModel.getDepartments(companyId)
                        profileViewModel.getDecisionGroups(companyId)
                    }
                    setProfileData(it.data)
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
        profileViewModel.departmentsVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    departmentList = (it.data as ArrayList<Departments>?)!!
                    binding.departmentSpinner.setOnClickListener {
                        val dropDownList = ArrayList<DropDownModel>()
                        departmentList.forEach { department ->
                            val dropDownModel = DropDownModel(
                                department.companyId,
                                department.id,
                                department.name,
                                department.note,
                                department.status
                            )
                            dropDownList.add(dropDownModel);
                        }
                        showBottomSheetDropDown(dropDownList, DropDownType.DEPARTMENT);
                        hideSoftKeyboard()
                    }
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
        profileViewModel.designationsVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    jobTitleList = (it.data as ArrayList<Designations>?)!!
                    binding.jobTitleSpinner.setOnClickListener {
                        val dropDownList = ArrayList<DropDownModel>()
                        jobTitleList.forEach { department ->
                            val dropDownModel = DropDownModel(
                                department.companyId,
                                department.id,
                                department.name,
                                department.note,
                                department.status
                            )
                            dropDownList.add(dropDownModel);
                        }
                        showBottomSheetDropDown(dropDownList, DropDownType.DESIGNATION);
                        hideSoftKeyboard()
                    }
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
        profileViewModel.decisionGroupsVMLD.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    decisionGroupList = (it.data as ArrayList<DecisionGroups>?)!!
                    binding.decisionGroupSpinner.setOnClickListener {
                        val dropDownList = ArrayList<DropDownModel>()
                        decisionGroupList.forEach { department ->
                            val dropDownModel = DropDownModel(
                                department.companyId,
                                department.id,
                                department.name,
                                department.note,
                                department.status
                            );
                            dropDownList.add(dropDownModel);
                        }
                        showBottomSheetDropDown(dropDownList, DropDownType.DECISION_GROUP)
                        hideSoftKeyboard()
                    }
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
    }

    private fun setProfileData(data: ResponsePersonalProfile?) {
        if (data != null) {

            binding.firstName.setText(data.firstName)
            binding.lastName.setText(data.lastName)
            binding.email.text = data.email
            var roleList = ""
            data.roles?.forEach { roles ->
                roleList += "${roles},"
            }
            roleList.lowercase()
            binding.deciseRoleEt.text = roleList
            binding.phoneNumberEt.setText(data.phoneNumber)

            binding.updateBtn.setOnClickListener {

                val request = RequestUpdatePersonalProfile(
                    firstName = binding.firstName.text.toString(),
                    lastName = binding.lastName.text.toString(),
                    countryCode = data.countryCode,
                    department = binding.departmentSpinner.text.toString(),
                    decisionGroups = null,
                    //TODO decisionGroups
                    //decisionGroups = binding.decisionGroupSpinner.text.toString(),
                    designation = binding.jobTitleSpinner.text.toString(),
                    id = tokenManager.getUserID(Constants.USER_ID).toInt(),
                    phoneNumber = binding.phoneNumberEt.text.toString()
                )
            }
        }

    }


    private fun showBottomSheetDropDown(
        dropDownList: ArrayList<DropDownModel>,
        dropDownType: DropDownType
    ) {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_dialog)
        bottomSheetDialog.behavior.maxHeight = 1000 // set max height when expanded in PIXEL
        bottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        // bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        bottomSheetDialog.findViewById<ImageView>(R.id.cancel_buttonSheet)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        //  bottomSheetDialog.behavior.peekHeight = 400 // set default height when collapsed in PIXEL
        // val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)



        buildDropDownRecyclerView(recyclerView!!, dropDownList, dropDownType)
        val searchView = bottomSheetDialog.findViewById<EditText>(R.id.searchText)
        searchView!!.onTextChanged {
            filterDropDownItem(dropDownList, it.trim())
        }
        bottomSheetDialog.show()
    }

    private fun buildDropDownRecyclerView(
        recyclerView: RecyclerView,
        dropDownList: ArrayList<DropDownModel>,
        dropDownType: DropDownType
    ) {
        dropDownAdapter = DropDownAdapter(this, dropDownType) // 'this' refers to the fragment
        dropDownAdapter.submitInitialList(dropDownList)
        recyclerView.adapter = dropDownAdapter
    }

    private fun filterDropDownItem(dropDownList: ArrayList<DropDownModel>, text: String) {
        // creating a new array list to filter our data.
        val filteredList = ArrayList<DropDownModel>()

        // running a for loop to compare elements.
        for (item in dropDownList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name!!.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)!!.gone()
            bottomSheetDialog.findViewById<RecyclerView>(R.id.noDataTv)!!.show()


        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            dropDownAdapter.filterList(filteredList)
            bottomSheetDialog.findViewById<RecyclerView>(R.id.noDataTv)!!.gone()
            bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)!!.show()

        }
    }

    override fun selectedDropDownItem(dropDownModel: DropDownModel, dropDownType: DropDownType) {
        when (dropDownType) {
            DropDownType.DEPARTMENT -> {
                binding.departmentSpinner.text = dropDownModel.name
            }

            DropDownType.DESIGNATION -> {
                binding.jobTitleSpinner.text = dropDownModel.name
            }

            DropDownType.DECISION_GROUP -> {
                binding.decisionGroupSpinner.text = dropDownModel.name
            }
        }
        bottomSheetDialog.dismiss()
    }

}