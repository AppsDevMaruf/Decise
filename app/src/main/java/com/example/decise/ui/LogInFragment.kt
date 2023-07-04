package com.example.decise.ui

import androidx.appcompat.app.AppCompatActivity
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentLogInBinding


class LogInFragment : BaseFragment<FragmentLogInBinding>() {

    override fun getFragmentView(): Int {
        return R.layout.fragment_log_in
    }

    override fun configUi() {

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