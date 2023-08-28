package com.example.decise.data.networks.auth.verifyEmail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseVerifyEmail(
    var subscriptionType: String? = null,
    var signupType: String? = null,
    var email: String? = null
) : Parcelable
