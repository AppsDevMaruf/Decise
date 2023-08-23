package com.example.decise.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.SmsManager
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.decise.BuildConfig
import com.example.decise.data.prefs.PrefKeys
import com.example.decise.data.prefs.PreferenceManager
import java.io.File
import java.io.IOException
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


class AppUtils private constructor() {

    companion object {
        val shared = AppUtils()
    }

    var mLastClickTime = 0L
    val isDebug: Boolean
        get() = BuildConfig.DEBUG

    fun isOpenRecently(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }

    fun getAppVersion(@NonNull context: Context): Int {
        var currentAppVersion = 0
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val longVersionCode = PackageInfoCompat.getLongVersionCode(pInfo)
            currentAppVersion = longVersionCode.toInt()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return currentAppVersion
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().getDisplayMetrics().widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().getDisplayMetrics().heightPixels
    }

    fun getVersionName(context: Context): String {
        return context.packageManager
            .getPackageInfo(context.packageName, 0).versionName
    }


    fun openAppOnPlayStore(@NonNull context: Context, packageName: String = "") {
        var appPackageName = packageName
        if (packageName == "") {
            appPackageName = context.applicationContext.packageName
        }
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: android.content.ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    fun appInstalledOrNot(uri: String, context: Context, packageManager: PackageManager): Boolean {

        return try {
            val pm: PackageManager = context.getPackageManager()
            val info = pm.getPackageInfo("" + uri, PackageManager.GET_META_DATA)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


    fun downloadPdf(baseActivity: Context, url: String?, title: String?): Long {
        val direct = File(Environment.getExternalStorageDirectory().toString() + "")

        if (!direct.exists()) {
            direct.mkdirs()
        }
        val extension = url?.substring(url.lastIndexOf("."))
        val downloadReference: Long
        var dm: DownloadManager
        dm = baseActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "pdf" + System.currentTimeMillis() + extension
        )
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle(title)
        //Toast.makeText(baseActivity, baseActivity.getString(R.string.start_download), Toast.LENGTH_SHORT).show()

        downloadReference = dm?.enqueue(request) ?: 0

        return downloadReference

    }


    fun showDialogOnSpecificTIme(
        mPref: PreferenceManager,
        unit: TimeUnit,
        timeCount: Int
    ): Boolean {
        val timeFromPrefs = mPref.getLong(PrefKeys.POPUPNOTIFICAITON_TIME, 0)
        Log.d("gettime", timeFromPrefs.toString())

        when (unit) {
            TimeUnit.MINUTES -> {
                val TIME_DIFF = (timeCount * 60 * 1000).toLong()
                if (System.currentTimeMillis() - timeFromPrefs > TIME_DIFF) {
                    mPref.putLong(PrefKeys.POPUPNOTIFICAITON_TIME, System.currentTimeMillis())
                    return true
                } else {
                    return false
                }

            }

            TimeUnit.HOURS -> {}
            TimeUnit.SECONDS -> {}
            TimeUnit.DAYS -> {
//              val TIME_DIFF = (24*60 * 60 * 1000).toLong()
//              if (System.currentTimeMillis() - timeFromPrefs > TIME_DIFF) {
//                  mPref.putLong(PrefKeys.POPUPNOTIFICAITON_TIME, System.currentTimeMillis())
//                  return  true
//              }else{
//                  return false
//              }
            }

            else -> {}
        }
        return false

    }


    fun hideKeyboard(activity: Activity) {
        val v: View? = activity.currentFocus
        if (v != null) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
        }
    }


    fun dpFromPx(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }


    @SuppressLint("HardwareIds")
    fun getDeviceId(@NonNull context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }


    /* ------------------------------------------
        Get Launcher Activity name
        ------------------------------------------
        This method return the name of launcher activity of the app
     */

    fun getLauncherActivity(@NonNull context: Context): String {
        var activityName = ""
        val pm = context.packageManager
        val intent = pm.getLaunchIntentForPackage(context.applicationContext.packageName)
        val activityList = pm.queryIntentActivities(intent!!, 0)
        if (activityList != null) {
            activityName = activityList[0].activityInfo.name
        }
        return activityName

    }

    /*-------------------------------------------
        Check a service is running or not
    ----------------------------------------------------- */
    fun <S : Service> isServiceRunning(
        context: Context,
        serviceClass: Class<S>
    ): Boolean {

        val activityManager =
            context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(Integer.MAX_VALUE)

        for (runningServiceInfo in runningServices) {
            if (serviceClass.name == runningServiceInfo.service.className) {
                return true
            }
        }
        return false
    }



    fun dial(context: Context, phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(dialIntent)
    }

    @SuppressLint("MissingPermission")
    fun call(context: Context, phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(callIntent)
    }

    fun openCamera(context: Context, REQUEST_CODE: Int) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(context.packageManager) != null) {
            (context as AppCompatActivity).startActivityForResult(cameraIntent, REQUEST_CODE)
        }
    }

    fun openCamera(context: Context, imageUri: Uri, REQUEST_CODE: Int) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        if (cameraIntent.resolveActivity(context.packageManager) != null) {
            (context as AppCompatActivity).startActivityForResult(cameraIntent, REQUEST_CODE)
        }
    }

    fun openGallery(context: Context, REQUEST_CODE: Int) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        (context as AppCompatActivity).startActivityForResult(galleryIntent, REQUEST_CODE)
    }

    fun goToSettings(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.parse("package:" + context.applicationContext.packageName)
        intent.data = uri
        context.startActivity(intent)
    }

    fun openLinkOnExternalBrowser(context: Context, link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(browserIntent)
    }

    fun openFacebookPage(context: Context, FB_PAGE_URL: String) {
        return try {
            context.packageManager.getPackageInfo("com.facebook.katana", 0)
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("fb://facewebmodal/f?href=$FB_PAGE_URL")
                )
            )
        } catch (e: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(FB_PAGE_URL)))
        }
    }


    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = AppCompatResources.getDrawable(context, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable!!)).mutate()
        }

        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    @SuppressWarnings("deprecation")
    fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    fun showIntegerValueWithAnim(textView: TextView, value: Int) {
        val animator: ValueAnimator = ValueAnimator.ofInt(0, value)
        animator.duration = 500
        animator.addUpdateListener {
            textView.text = "${animator.animatedValue}"
        }
        animator.start()
    }

    fun sendSMS(
        mContext: Context,
        phoneNo: String,
        messageInfo: String
    ) {
        val SENT = "SMS_SENT"
        val DELIVERED = "SMS_DELIVERED"
        try {
            val sentPI = PendingIntent.getBroadcast(mContext, 0, Intent(SENT),
                PendingIntent.FLAG_IMMUTABLE)
            val deliveredPI = PendingIntent.getBroadcast(mContext, 0, Intent(DELIVERED),
                PendingIntent.FLAG_IMMUTABLE)


            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNo, null, messageInfo, sentPI, deliveredPI)
            AlertService().showAlert(mContext, null, "আপনার জরুরী ম্যাসেজটি পাঠানো হয়েছে।")
        } catch (ex: Exception) {
            mContext.showToast("${ex.message}")
        }
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getInBangla(string: String): String {
        val banglaNumber = arrayOf('১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯', '০')
        val engNumber = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')
        var values = ""
        val character = string.toCharArray()
        for (i in character.indices) {
            var c: Char? = ' '
            for (j in engNumber.indices) {
                if (character[i] == engNumber[j]) {
                    c = banglaNumber[j]
                    break
                } else {
                    c = character[i]
                }
            }
            values += c!!
        }
        return values
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        cursor?.let {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                res = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        return res

    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        var image: Bitmap? = null
        try {
            val url = URL(src)
            image = (BitmapFactory.decodeStream(url.openConnection().getInputStream()) as Bitmap?)!!
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return image
    }


//    fun getBengaliMonth(month: String): String {
//        val bengaliMonths = arrayOf(
//            "জানুয়ারি",
//            "ফেব্রুয়ারী",
//            "মার্চ",
//            "এপ্রিল",
//            "মে",
//            "জুন",
//            "জুলাই",
//            "আগস্ট",
//            "সেপ্টেম্বর",
//            "অক্টোবর",
//            "নভেম্বর",
//            "ডিসেম্বর"
//        )
//        val englishMonth = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")
//        return ""
//    }
}

