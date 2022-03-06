package com.checkoutcom.checkoutpractical.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.checkoutcom.checkoutpractical.R
import com.checkoutcom.checkoutpractical.databinding.ActivityMainBinding
import com.checkoutcom.checkoutpractical.extensions.snackbar
import com.checkoutcom.checkoutpractical.ui.fragments.PaymentConclusionFragmentDirections
import com.checkoutcom.checkoutpractical.ui.fragments.SecurePaymentWebviewFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpNavigation()
    }

    // Configuring navigation
    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        addDestinationChangeListener()
    }


    // customize toolbar backbutton to maintain back stack
    private fun addDestinationChangeListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationOnClickListener {
                when (destination.id) {
                    R.id.ProceedToPayFragment -> {
                        finish()
                    }
                    R.id.SecurePaymentWebviewFragment -> {
                        navController.navigate(SecurePaymentWebviewFragmentDirections.actionSecurePaymentWebviewFragmentToProceedToPayFragment())
                    }
                    R.id.PaymentConclusionFragment -> {
                        navController.navigate(PaymentConclusionFragmentDirections.actionPaymentConclusionFragmentToProceedToPayFragment())
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //disable system back button at the moment
    override fun onBackPressed() {
        binding.parentConstrainLayout.snackbar(getString(R.string.msg_use_ui_back))
    }

}