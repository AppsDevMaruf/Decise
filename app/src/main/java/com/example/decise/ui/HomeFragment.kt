package com.example.decise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getFragmentView(): Int {
        return  R.layout.fragment_home
    }

}