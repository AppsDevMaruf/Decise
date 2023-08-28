package com.example.decise.interfaces

import com.example.decise.data.networks.profile.DropDownModel
import com.example.decise.utils.DropDownType

interface DropDownInteractionListener {
    fun selectedDropDownItem(dropDownModel: DropDownModel, dropDownType: DropDownType)
}