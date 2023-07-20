package com.example.decise.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView


fun dpFromPx(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}

fun pxFromDp(context: Context, dp: Float): Float {
    return (dp * context.resources.displayMetrics.density) / 3
}

fun TextView.fontSize(context: Context, px: Float) {
    val fontsize = px2dip(context, px).toFloat()
    textSize = fontsize

}
val Context.isConnected: Boolean
    get() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }

fun Activity.iconColor(activity: Activity) {


    if (Build.VERSION.SDK_INT >= 23) {
        val decor = activity.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}


fun pxToDp(textView: TextView, pxs: String) {
    val px: Int = pxs.toInt()

    val size = (px / Resources.getSystem().displayMetrics.density) as Float
    textView.textSize = size

}

fun TextView.px2dip(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale * 2.5).toInt()
}
fun EditText.addExpiryDateTextWatcher() {
    val slashChar = '/'
    val maxMonthValue = 12

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed for this implementation
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Not needed for this implementation
        }

        override fun afterTextChanged(s: Editable?) {
            s?.let {
                val input = s.toString()

                // Remove previous listeners to avoid recursive calls
                this@addExpiryDateTextWatcher.removeTextChangedListener(this)

                // Add a slash after two characters (month)
                if (s.length == 2 && s[1] != slashChar) {
                    s.insert(2, slashChar.toString())
                }

                // Validate month
                val month = input.substringBefore(slashChar)
                if (!month.isEmpty() && (month.toInt() < 1 || month.toInt() > maxMonthValue)) {
                    // Optionally, you can show an error message in a separate TextView here
                    // For simplicity, we'll use logcat to display the error message.
                    println("Invalid month")
                }

                // Reattach the TextWatcher
                this@addExpiryDateTextWatcher.addTextChangedListener(this)
            }
        }
    })
}


