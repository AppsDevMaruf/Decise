package com.example.decise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentOpenEmailAppBinding
import com.example.decise.utils.toast


class OpenEmailAppFragment : BaseFragment<FragmentOpenEmailAppBinding>() {

    override fun getFragmentView(): Int {
        return R.layout.fragment_open_email_app
    }

    override fun configUi() {
        binding.gmailAppBtn.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:recipient@gmail.com")

            val activities = requireActivity().packageManager.queryIntentActivities(emailIntent, 0)

            if (activities.isNotEmpty()) {
                val chooserIntent = Intent.createChooser(emailIntent, "Choose an email app")
                startActivity(chooserIntent)
            } else {
                Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show()
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