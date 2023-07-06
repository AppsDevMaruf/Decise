package com.example.decise.viewmodel

import androidx.lifecycle.ViewModel
import com.example.decise.repos.SecuredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val securedRepository: SecuredRepository) :
    ViewModel() {
       }