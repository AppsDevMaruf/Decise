package com.example.decise

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentVerifyEmailBinding


class VerifyEmailFragment : BaseFragment<FragmentVerifyEmailBinding>() {
    private val arg: VerifyEmailFragmentArgs by navArgs()
    var result = ""
    override fun getFragmentView(): Int {
        return R.layout.fragment_verify_email
    }

    override fun configUi() {
        val subscriptionType = arg.subscriptionType
        val signupType = arg.signupType
        val email = arg.email
        val evc = arg.evc
        binding.message.text = "$subscriptionType\n$signupType\n$email\n$evc"

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