package com.checkoutcom.checkoutpractical.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.checkoutcom.checkoutpractical.R
import com.checkoutcom.checkoutpractical.databinding.ActivityMainBinding
import com.checkoutcom.checkoutpractical.extensions.snackbar
import com.checkoutcom.checkoutpractical.ui.fragments.PaymentConclusionFragmentDirections
import com.checkoutcom.checkoutpractical.ui.fragments.ProceedToCheckoutFragmentDirections
import com.checkoutcom.checkoutpractical.ui.fragments.SecurePaymentWebviewFragmentDirections
import com.checkoutcom.checkoutpractical.ui.viewmodels.CheckOutMainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val checkOutMainViewModel: CheckOutMainViewModel by viewModels()

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
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationOnClickListener {
                when (destination.id) {
                    R.id.checkoutSDKDemoFragment -> {
                        finish()
                    }
                    R.id.ProceedToPayFragment -> {
                        navController.navigate(ProceedToCheckoutFragmentDirections.actionProceedToPayFragmentToCheckoutSDKDemoFragment())
                    }
                    R.id.SecurePaymentWebviewFragment -> {
                        navigateToConclusionFragment()
                    }
                    R.id.PaymentConclusionFragment -> {
                        navController.navigate(PaymentConclusionFragmentDirections.actionPaymentConclusionFragmentToCheckoutSDKDemoFragment())
                    }
                }
            }
        }
    }

    private fun navigateToConclusionFragment() {
        // check if the payment is in processing state means user entered the secure code, restrict user to leave the screen and showing useful toast for that
        if (checkOutMainViewModel.getPaymentStatus() != getString(R.string.payment_in_progress)) {
            navController.navigate(SecurePaymentWebviewFragmentDirections.actionSecurePaymentWebviewFragmentToProceedToPayFragment())
        } else {
            binding.parentConstrainLayout.snackbar(getString(R.string.dont_press_back_while_payment))
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