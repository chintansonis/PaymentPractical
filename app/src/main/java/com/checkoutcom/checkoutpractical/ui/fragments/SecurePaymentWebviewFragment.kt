package com.checkoutcom.checkoutpractical.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.checkoutcom.checkoutpractical.R
import com.checkoutcom.checkoutpractical.databinding.FragmentSecurePaymentWebviewBinding
import com.checkoutcom.checkoutpractical.ui.viewmodels.SecurePaymentWebviewViewModel


/**
 * SecurePaymentWebviewFragment responsible for 3dSecure payment
 */
class SecurePaymentWebviewFragment : Fragment() {

    private var _binding: FragmentSecurePaymentWebviewBinding? = null
    private val securePaymentWebviewViewModel: SecurePaymentWebviewViewModel by viewModels()


    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSecurePaymentWebviewBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            binding.url = SecurePaymentWebviewFragmentArgs.fromBundle(bundle).url
        }
        initWebView()
        observeUI()

        // throwing user out of the app, if pressing back from 3dsecure screen
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.webView.canGoBack()) {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun observeUI() {
        // manage progressbar on Data loading
        securePaymentWebviewViewModel.isDataLoading.observe(viewLifecycleOwner) { isDataLoad ->
            when {
                isDataLoad -> {
                    binding.linearProgressBarLayout.visibility = View.VISIBLE
                    binding.webView.visibility = View.GONE
                }
                else -> {
                    binding.linearProgressBarLayout.visibility = View.GONE
                    binding.webView.visibility = View.VISIBLE
                }
            }
        }

        // Observing paymentConfirmation message, accordingly redirect to PaymentConclusionFragment
        securePaymentWebviewViewModel.paymentConclusionMessage.observe(viewLifecycleOwner) { paymentMessage ->
            findNavController().navigate(SecurePaymentWebviewFragmentDirections.actionSecurePaymentWebviewFragmentToPaymentConclusionFragment(
                paymentMessage))
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                securePaymentWebviewViewModel.setisDataLoading(true)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView, url: String) {
                securePaymentWebviewViewModel.setisDataLoading(false)
                setPaymentConclusionMessage(url)
            }
        }


    }

    /**
     * checking url from web view for success and failure payment, accordingly settingup Payment confirmation message
     */
    private fun setPaymentConclusionMessage(url: String) {
        when {
            url.contains(getString(R.string.success_url)) -> {
                securePaymentWebviewViewModel.setPaymentConclusionMessage(getString(R.string.payment_success_msg))
            }
            url.contains(getString(R.string.fail_url)) -> {
                securePaymentWebviewViewModel.setPaymentConclusionMessage(getString(R.string.payment_fail_msg))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}