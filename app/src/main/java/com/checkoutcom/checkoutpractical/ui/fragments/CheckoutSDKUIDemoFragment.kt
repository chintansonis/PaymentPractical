package com.checkoutcom.checkoutpractical.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.checkout.android_sdk.PaymentForm
import com.checkout.android_sdk.PaymentForm.On3DSFinished
import com.checkout.android_sdk.PaymentForm.PaymentFormCallback
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.network.NetworkError
import com.checkoutcom.checkoutpractical.databinding.FragmentSdkUiBinding
import com.checkoutcom.checkoutpractical.extensions.alert
import com.checkoutcom.checkoutpractical.extensions.neutralButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * CheckoutSDKUIDemoFragment showing final payment output
 */
@AndroidEntryPoint
class CheckoutSDKUIDemoFragment : Fragment() {

    private var _binding: FragmentSdkUiBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSdkUiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.checkoutCardForm
            .setFormListener(mFormListener)
            .set3DSListener(m3DSecureListener)
            .setEnvironment(Environment.SANDBOX)
            .setKey("pk_test_b37b8b6b-fc9a-483f-a77e-3386b606f90e")
            .setDefaultBillingCountry(Locale.UK)
    }

    // Callback used for the Payment Form interaction
    val mFormListener by lazy {
        object : PaymentFormCallback {
        override fun onFormSubmit() {

        }

        override fun onTokenGenerated(response: CardTokenisationResponse) {
            // dismiss the loader
            binding.checkoutCardForm.clearForm() // clear the form
            //displayMakePaymentMessage(response.token)
            displayMessage("Token  generated",response.token)
        }

        override fun onError(response: CardTokenisationFail) {
            // mProgressDialog.dismiss() // dismiss the loader
            // displayMessage("Token Error", response.errorType, false)
            displayMessage("Token Error",response.errorType)
        }

        override fun onNetworkError(error: NetworkError) {
            //  mProgressDialog.dismiss() // dismiss the loader
            // displayMessage("Network Error", error.toString(), false)
            displayMessage("Network Error",error.toString())
        }

        override fun onBackPressed() {
            //  finish()
        }
    }
    }
    private val m3DSecureListener: On3DSFinished = object : On3DSFinished {
        override fun onSuccess(token: String) {
           //displayMessage("Result", "Authentication success: $token", true)
        }

        override fun onError(errorMessage: String) {
            //displayMessage("Result", "Authentication failure: $errorMessage", true)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun displayMessage(title: String, message: String) {
        requireContext().alert {
            setTitle(title)
            setMessage(message)
            neutralButton { }
        }

    }
}