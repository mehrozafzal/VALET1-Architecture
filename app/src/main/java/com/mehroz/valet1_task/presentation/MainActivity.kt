package com.mehroz.valet1_task.presentation

import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mehroz.valet1_task.R
import com.mehroz.valet1_task.base.BaseActivity
import com.mehroz.valet1_task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onInit() {
        setSupportActionBar(binding.activityMainToolbarLayout.toolbar)
        navController = findNavController(R.id.activityMain_navHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.activityMainDrawerLayout)
        binding.activityMainNavView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        initListener()
    }

    override fun screenName(): String = MainActivity::class.java.simpleName

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initListener() {
        binding.activityMainNavView.setNavigationItemSelectedListener { menuItem ->
            binding.activityMainDrawerLayout.closeDrawers()
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.item_home -> navController.navigate(R.id.homeFragment)
                R.id.item_devices -> navController.navigate(R.id.myDevicesFragment)
                R.id.item_settings -> navController.navigate(R.id.settingsFragment)
            }
            true
        }


        /*    navController.addOnDestinationChangedListener { _, _, _ ->
                if (binding.activityMainDrawerLayout.isDrawerOpen(GravityCompat.START))
                    binding.activityMainDrawerLayout.closeDrawer(GravityCompat.START)
            }*/
   /*     navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.myDevicesFragment, R.id.settingsFragment -> {
                    // binding.activityMainToolbarLayout.toolbar.visibility = View.GONE
                    // binding.activityMainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    //    binding.activityMainToolbarLayout.toolbar.visibility = View.VISIBLE
                    //    binding.activityMainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
        }*/
    }

    override fun onBackPressed() {
        if (binding.activityMainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.activityMainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            return super.onBackPressed()
        }
    }
}