package com.example.decise.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.decise.R
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun Fragment.toast(str: String) {
    Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show()

}



fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


fun View.show() {
    visibility = View.VISIBLE
}


fun Fragment.enableBtn(given: Boolean, btn: Button) {

    // binding.signInBtn.isEnabled = emailGiven && passwordGiven
    btn.isEnabled = given

    if (btn.isEnabled) {
        btn.background =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.button_login_bg_orange_enable)
    } else {
        btn.background =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.button_primary_bg_orange_disable)


    }


}

fun Any.nameAbbreviationGenerator(name: String): String? {
    val lens = name.length - 1
    val lastChar = name[lens]
    var temp = ""
    val arr = mutableListOf<String>()
    for (item in name) {

        if (item != ' ') {
            temp += item
        }
        if ((item == ' ' && item + 1 == ' ')) continue
        if ((item == ' ' && temp != "") || lastChar == item) {
            arr.add(temp)
            temp = ""
        }
    }
    val len = arr.size - 1
    val firstnameAB = arr[0][0]
    val lastnameAB = arr[len][0]
    return "$firstnameAB$lastnameAB"
}

fun Fragment.hideSoftKeyboard() {
    val inputMethodManager =
        requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = requireActivity().currentFocus
    val windowToken = requireActivity().window?.decorView?.rootView?.windowToken
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    inputMethodManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
    if (currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Any.requestPermissions(
    request: ActivityResultLauncher<Array<String>>,
    permissions: Array<String>
) = request.launch(permissions)

fun Activity.isAllPermissionsGranted(permissions: Array<String>) = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.isAllPermissionsGranted(permissions: Array<String>) = permissions.all {
    ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
}

fun Activity.hideSoftKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = this.currentFocus
    val windowToken = this.window?.decorView?.rootView?.windowToken
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    inputMethodManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
    if (currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}



fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s.toString().trim())
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}



fun Any.removeUnderscore(originalString: String): String? {
    val str = originalString.lowercase(Locale.ROOT)
    val stringWithoutUnderscores = str.replace("_", " ")
        .split(" ")
        .joinToString(" ")
        {
            it.replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(
                    Locale.ROOT
                ) else char.toString()
            }
        }
    return stringWithoutUnderscores
}

@SuppressLint("SimpleDateFormat")
fun Any.getZonedTime(zoneTime: String): String {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val parsed =
            ZonedDateTime.parse(zoneTime, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(
                ZoneId.of(TimeZone.getDefault().id)
            )
        //  val parsedDate = LocalDateTime.parse(zoneTime, DateTimeFormatter.ISO_DATE_TIME)
        parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm a"))
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd 'T' HH:mm")
        val formatter = SimpleDateFormat("dd.MM.yyyy hh:mm a")
        formatter.format(parser.parse(zoneTime))
    }



}

fun TextView.setTextNonNull(str: String?) {
    str?.let {
        this.text = it
    }
}

fun String.titleCaseFirstChar() = replaceFirstChar(Char::titlecase)

