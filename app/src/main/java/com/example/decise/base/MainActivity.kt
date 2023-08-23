package com.example.decise.base

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.exifinterface.media.ExifInterface
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.canhub.cropper.CropImage.CancelledResult.bitmap
import com.example.decise.R
import com.example.decise.data.prefs.PreferenceManager
import com.example.decise.databinding.ActivityMainBinding
import com.example.decise.di.SocketHandler
import com.example.decise.utils.AlertService
import com.example.decise.utils.AppConstants
import com.example.decise.utils.AppConstants.REQUEST_CAMERA
import com.example.decise.utils.AppUtils
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.PermissionUtils
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.viewmodel.NotificationsViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var userProfilePicHeader: ShapeableImageView
    private lateinit var uploadProfilePicBtn: ImageView
    private lateinit var userProfilePicABHeader: TextView
    private lateinit var profilePicAB: TextView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var nav: View
    private val notificationsViewModel by viewModels<NotificationsViewModel>()
    private var GALLERY_PERMISSION = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var permissionUtils: PermissionUtils

    @Inject
    lateinit var mAlertService: AlertService

    @Inject
    lateinit var prf: PreferenceManager

    companion object {
        private var imageUri: Uri? = null
        private var imagePath: String? = null
    }

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide()
        setContentView(binding.root)

        // The following lines connects the Android app to the server.

        // args[0] is the data from the server
// Change "as Int" according to the data type
// Example "as String" or write nothing
// Logging the data is optional
        binding.toolbar.notification.setOnClickListener {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()

        }


        //notificationsViewModel.getNotificationByCompanyIdAndStatusVM(24, true, 0, 5)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navigationView)
        navView.itemIconTintList = null


        val navController = findNavController(R.id.nav_host_fragment)

        nav = binding.navigationView.getHeaderView(0)
        userProfilePicHeader = nav.findViewById(R.id.userProfilePicHeader)
        uploadProfilePicBtn = nav.findViewById(R.id.uploadProfilePicBtn)
        uploadProfilePicBtn.setOnClickListener {
            showImagePickerDialog()
        }

        userProfilePicABHeader = nav.findViewById(R.id.profilePicABHeader)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.toolbar.profilePicAB.setOnClickListener {
            navController.navigate(R.id.profileFragment)
        }
        binding.toolbar.userProfilePic.setOnClickListener {
            navController.navigate(R.id.profileFragment)
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.homeFragment -> {
                    navController.navigateUp() // to clear previous navigation history
                    navController.navigate(R.id.homeFragment)
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.logoutFragment -> {
                    navController.navigateUp() // to clear previous navigation history
                    navController.navigate(R.id.logoutFragment)
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                else -> {
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstants.REQUEST_CAMERA ->
                if (grantResults.isNotEmpty()) {
                    var isBothPermissionGranted = true
                    for (i in permissions.indices) {
                        if (permissionUtils.shouldAskPermission(this, permissions[i])) {
                            isBothPermissionGranted = false
                            break
                        }
                    }
                    if (isBothPermissionGranted) {
                        takePicture()
                    } else {
                        manageCameraAndStoragePermission()
                    }
                }

            AppConstants.REQUEST_GALLERY -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }

    }

    private fun manageGallery() {
        permissionUtils.checkPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            object : PermissionUtils.OnPermissionAskListener {
                override fun onNeedPermission() {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        GALLERY_PERMISSION,
                        AppConstants.REQUEST_GALLERY
                    )
                }

                override fun onPermissionPreviouslyDenied() {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        GALLERY_PERMISSION,
                        AppConstants.REQUEST_GALLERY
                    )
                }

                override fun onPermissionPreviouslyDeniedWithNeverAskAgain() {
                    mAlertService.showConfirmationAlert(this@MainActivity,
                        getString(R.string.permission_required),
                        getString(R.string.permission_storage_denied_one),
                        getString(R.string.button_not_now),
                        getString(R.string.okay),
                        object : AlertService.AlertListener {

                            override fun negativeBtnDidTapped() {}

                            override fun positiveBtnDidTapped() {
                                AppUtils.shared.goToSettings(this@MainActivity)
                            }
                        })

                }

                override fun onPermissionGranted() {
                    openGallery()
                }

            })

    }

    /** Creating Image File  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMG_$timeStamp"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,
            /** prefix */
            ".jpg",
            /** suffix */
            storageDir
            /** directory */
        ).apply {
            imagePath = absolutePath
        }
    }

    /** Dispatching Camera Intent  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            var imageFile: File? = null
            try {
                imageFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            imageFile?.let {
                val authorities: String = applicationContext.packageName + ".provider"
                /** Authorities That we provided in Manifest.xml*/
                imageUri = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    Uri.fromFile(imageFile)
                } else {
                    FileProvider.getUriForFile(this, authorities, imageFile)
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                takePhotoForResult.launch(intent)
            }
        }
    }

    private fun openGallery() {

        val pickIntent = Intent(Intent.ACTION_PICK)
        pickIntent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        pickImageFromGalleryForResult.launch(pickIntent)


    }

    private fun getBitmapFromImagePath(imagePath: String?): Bitmap? {
        imagePath?.let {
            val imageFile = File(imagePath)
            if (imageFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                val ei = ExifInterface(imageFile.absolutePath)
                val orientation = ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
                val rotatedBitmap: Bitmap? = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(
                        myBitmap,
                        90
                    )

                    ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(
                        myBitmap,
                        180
                    )

                    ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(
                        myBitmap,
                        270
                    )

                    ExifInterface.ORIENTATION_NORMAL -> myBitmap
                    else -> myBitmap
                }
                return rotatedBitmap
            }
            return null
        }
        return null
    }

    private fun manageCameraAndStoragePermission() {
        val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val cameraPermission = Manifest.permission.CAMERA
        val listOfPermissionNeeded = ArrayList<String>()
        if (!hasCameraPermission()) {
            listOfPermissionNeeded.add(cameraPermission)
        }
        if (!hasStoragePermission()) {
            listOfPermissionNeeded.add(storagePermission)
        }
        mAlertService.showConfirmationAlert(this,
            getString(R.string.permission_required),
            getString(R.string.permission_msg_for_camera),
            getString(R.string.button_not_now),
            getString(R.string.buttoin_continue),
            object : AlertService.AlertListener {

                override fun negativeBtnDidTapped() {}

                override fun positiveBtnDidTapped() {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        listOfPermissionNeeded.toTypedArray(),
                        AppConstants.REQUEST_CAMERA
                    )
                }
            })

    }

    /** Checking Camera Permission  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    /** Checking Storage Permission  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    private fun hasStoragePermission(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> true
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

            else -> true
        }
    }

    private val takePhotoForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                when (result.resultCode) {
                    REQUEST_CAMERA -> {
                        val imageBitmap = data?.extras?.get("data") as Bitmap
                        userProfilePicHeader.setImageBitmap(imageBitmap)
                    }

                }

            }
        }

    private val pickImageFromGalleryForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == AppConstants.REQUEST_GALLERY) {
                val intent = result.data
                // handle image from gallery
                Log.d("TAG", "gallery:$intent ")
                if (intent != null) {
                    userProfilePicHeader.setImageURI(intent.data)
                }

                if (getIntent() != null && getIntent().data != null) {
                    userProfilePicHeader.setImageBitmap(bitmap)
                    //attachmentUri = getIntent().data
                    /*try {
                        val realImagePath = fileUploader.getRealPathFromUri(attachmentUri!!)
                        if (realImagePath != null) {
                            fileToUpload =
                                ImageCompresser.instance.getCompressedImageFile(
                                    realImagePath,
                                    this
                                )
                            val bitmap = BitmapFactory.decodeFile(fileToUpload!!.absolutePath)
                            binding.toolbar.userProfilePic.setImageBitmap(bitmap)

                        }
                    } catch (e: Exception) {
                        Log.e("LOG .....", "${e.localizedMessage}")
                    }*/
                }
            }
        }

    /** Checking Camera and Storage Permission  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    private fun hasCameraAndStoragePermission(): Boolean {
        return hasCameraPermission() && hasStoragePermission()
    }

    private fun showImagePickerDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_custom_layout)

        dialog.findViewById<TextView>(R.id.cancel_button).setOnClickListener {
            dialog.dismiss()
        }

        val galleryBtn = dialog.findViewById(R.id.galleryBtn) as TextView
        val cameraBtn = dialog.findViewById(R.id.cameraBtn) as TextView


        galleryBtn.setOnClickListener {
            manageGallery()
            dialog.dismiss()
        }

        cameraBtn.setOnClickListener {
            if (hasCameraAndStoragePermission()) {
                takePicture()
            } else {
                manageCameraAndStoragePermission()
            }
            dialog.dismiss()
        }

        dialog.show()

    }

    fun binObserver() {
        notificationsViewModel.getNotificationByCompanyIdAndStatusVMLD.observe(this) {
            binding.progressBar.gone()
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("TAG", "binObserver: ${it.data?.total.toString()} ")

                    Toast.makeText(this, "I am coming...", Toast.LENGTH_LONG).show()
                }

                is NetworkResult.Error -> {


                }

                is NetworkResult.Loading -> {
                    binding.progressBar.show()
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketHandler.closeConnection()
    }
}
