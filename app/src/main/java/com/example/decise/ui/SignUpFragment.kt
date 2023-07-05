package com.example.decise.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    override fun getFragmentView(): Int {
        return R.layout.fragment_sign_up
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