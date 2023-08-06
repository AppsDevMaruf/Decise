package com.example.decise.utils

import android.content.Context
import android.content.SharedPreferences

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs =
        context.getSharedPreferences(Constants.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(key: String, value: String) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun saveUserID(userID: String, value: String) {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(userID, value)
        editor.apply()
    }

    fun getUserID(userID: String): String {
        val sharedNameValue = prefs.getString(userID, Constants.NO_DATA)
        return sharedNameValue.toString()
    }
    fun getToken(key: String): String {
        val sharedNameValue = prefs.getString(key, Constants.NO_DATA)
        return sharedNameValue.toString()
    }



}