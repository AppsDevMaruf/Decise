package com.example.decise.ui.profile

import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.data.models.profile.departments.Departments
import com.example.decise.data.models.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.data.models.profile.update_personal_profile.RequestUpdatePersonalProfile
import com.example.decise.databinding.FragmentProfileBinding
import com.example.decise.interfaces.DepartmentSelectListener
import com.example.decise.ui.profile.adapter.DepartmentAdapter
import com.example.decise.utils.Constants
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.TokenManager
import com.example.decise.utils.enableBtn
import com.example.decise.utils.gone
import com.example.decise.utils.hideSoftKeyboard
import com.example.decise.utils.onTextChanged
import com.example.decise.utils.show
import com.example.decise.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(),DepartmentSelectListener{
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var departmentList: ArrayList<Departments>
    private lateinit var departmentAdapter: DepartmentAdapter
    @Inject
    lateinit var tokenManager: TokenManager
    override fun getFragmentView(): Int {
        return R.layout.fragment_profile
    }

    override fun configUi() {
        val userId = tokenManager.getUserID(Constants.USER_ID).toInt()
        profileViewModel.getProfileData(userId)

        buttonEnableAfterTextFillUp()
        binding.departmentSpinner.setOnClickListener {
            showBottomSheetDepartments()
            hideSoftKeyboard()
        }


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
                hasLastName = !it.trim().isNullOrBlank()
                binding.phoneNumberWarning.gone()
                enableBtn(hasFirstName && hasLastName && hasPhoneNumber, binding.updateBtn)
            } else {
                hasLastName = false
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
                    it.data?.forEach {
                        Log.d("TAG", "binObserver: ${it.name}")
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
            binding.email.setText(data.email)
            var roleList = ""
            data.roles?.forEach { roles ->
                roleList += "${roles},"
            }
            roleList.lowercase()
            binding.deciseRoleEt.text = roleList
            binding.phoneNumberEt.setText(data.phoneNumber)

            var decisionList = ""
            data.decisionGroups?.forEach { decisions ->
                decisionList += "${decisions},"
            }
            data.companyId?.let { profileViewModel.getDepartments(it) }

        }
        binding.updateBtn.setOnClickListener {
            val firstName = binding.firstName.text.toString()
            val request = RequestUpdatePersonalProfile(
                firstName = firstName,
                lastName = null,
                companyName = null,
                countryCode = null,
                department = data?.department,
                decisionGroups = null,
                designation = data?.designation,
                id = tokenManager.getUserID(Constants.USER_ID).toInt(),
                phoneNumber = binding.phoneNumberEt.text.toString()
            )
        }
    }
    private fun showBottomSheetDepartments() {
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



        buildDepartmentsRecyclerView(recyclerView!!)
        val searchView = bottomSheetDialog.findViewById<SearchView>(R.id.searchText)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filterDepartments(newText)
                return false
            }
        })

        bottomSheetDialog.show()
    }

    private fun filterDepartments(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<Departments>()

        // running a for loop to compare elements.
        for (item in departmentList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name!!.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            departmentAdapter.filterList(filteredlist)
        }
    }

    private fun buildDepartmentsRecyclerView(recyclerView: RecyclerView) {


        // initializing our adapter class.
        departmentAdapter = DepartmentAdapter(this, departmentList, requireContext())

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        recyclerView.layoutManager = manager

        // setting adapter to
        // our recycler view.
        recyclerView.adapter = departmentAdapter


    }

    override fun selectedDepartment(departments: Departments) {

    }

}