package com.example.decise.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.decise.R
import com.example.decise.databinding.ActivityMainBinding
import com.example.decise.di.SocketHandler
import com.example.decise.utils.NetworkResult
import com.example.decise.utils.gone
import com.example.decise.utils.show
import com.example.decise.viewmodel.NotificationsViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

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
