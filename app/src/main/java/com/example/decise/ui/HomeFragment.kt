package com.example.decise.ui

import com.example.decise.R
import com.example.decise.base.BaseFragment
import com.example.decise.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getFragmentView(): Int {
        return  R.layout.fragment_home
    }

}