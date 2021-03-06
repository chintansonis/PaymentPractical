package com.checkoutcom.checkoutpractical.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.checkout.android_sdk.CheckoutAPIClient
import com.checkout.android_sdk.CheckoutAPIClient.OnTokenGenerated
import com.checkout.android_sdk.Request.CardTokenisationRequest
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.network.NetworkError
import com.checkoutcom.checkoutpractical.R
import com.checkoutcom.checkoutpractical.databinding.FragmentCheckoutBinding
import com.checkoutcom.checkoutpractical.extensions.*
import com.checkoutcom.checkoutpractical.ui.viewmodels.ProceedCheckOutViewModel
import com.checkoutcom.checkoutpractical.utils.CardInputState
import com.checkoutcom.checkoutpractical.utils.CardUtil
import dagger.hilt.android.AndroidEntryPoint


/**
 * ProceedToCheckoutFragment is startDestination of navigation
 */
@AndroidEntryPoint
class ProceedToCheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val proceedToCheckoutviewModel: ProceedCheckOutViewModel by viewModels()
    private lateinit var mCheckoutAPIClient: CheckoutAPIClient

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        // bind lifecycleowner and viewmodel to view
        binding.apply {
            viewModel = proceedToCheckoutviewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        mCheckoutAPIClient = CheckoutAPIClient(
            requireContext(),
            resources.getString(R.string.secret_key),
            Environment.SANDBOX
        )
        mCheckoutAPIClient.setTokenListener(mTokenListener)
        /**
         * setCreditCardTextWatcher pattern work on preventing user to enter invalid past year for expiry date. For instance, user can not add
         * year as 2021. It restrict user to type 2021 fully.
         *
         */
        binding.etCardNumber.setCreditCardTextWatcher()

        binding.etExpiryDate.setExpiryDateFilter()

        observeUI()
        addTextChangeListeners()

        binding.btnProceed.setOnClickListener {
            requireContext().hideKeyboard(binding.btnProceed)
            if (!requireContext().isConnected) {
                view?.snackbar(getString(R.string.connection_error))
            } else {
                clearAllPreviousErrorMessages()
                proceedToCheckoutviewModel.onClickProceedToPayment()
            }
        }
    }

    /**
     * adding textchange listener for reset previous error messages
     */
    private fun addTextChangeListeners() {
        binding.etCardNumber.doAfterTextChanged { binding.textInputCardNumber.error = null }
        binding.etCardCvv.doAfterTextChanged { binding.textInputCvv.error = null }
        binding.etExpiryDate.doAfterTextChanged { binding.textInputExpiryDate.error = null }
    }

    /**
     * reset all errors on clicking of payment button
     */
    private fun clearAllPreviousErrorMessages() {
        binding.textInputCardNumber.error = null
        binding.textInputExpiryDate.error = null
        binding.textInputCvv.error = null
    }

    /**
     * observe method for setting network errors,
     * detecting card on typing card number and navigating to 3Dsecure screen on success response
     */
    private fun observeUI() {
        proceedToCheckoutviewModel.cardNumber.observe(viewLifecycleOwner) { value ->
            checkCardType(value)
        }

        proceedToCheckoutviewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                view?.snackbar(getString(it))
            }
        }

        proceedToCheckoutviewModel.responseCheckoutLiveData.observe(viewLifecycleOwner) { result ->
//            binding.progressBarCheckOut.visibility =
//                if (result is ResultHandler.Loading) View.VISIBLE else View.INVISIBLE
//            findNavController().navigate(
//                ProceedToCheckoutFragmentDirections.actionProceedToPayFragmentToSecurePaymentWebviewFragment(
//                    result.data?.url!!
//                )
//            )url
        }

        //observing cardInput state and trigger relevant messages to texxtinputlayout
        proceedToCheckoutviewModel.cardInputUiState.observe(viewLifecycleOwner) { cardInputUIState ->
            when (cardInputUIState) {
                is CardInputState.InValidCardNumberState -> {
                    binding.textInputCardNumber.error = getString(R.string.error_invalid_number)
                    binding.etCardNumber.requestFocus()
                }
                is CardInputState.InValidExpiryDateState -> {
                    binding.textInputExpiryDate.error =
                        getString(R.string.error_invalid_expiry_date)
                    binding.etExpiryDate.requestFocus()
                }
                is CardInputState.InValidCvvState -> {
                    binding.textInputCvv.error = getString(R.string.error_invalid_cvv)
                    binding.etCardCvv.requestFocus()
                }
                is CardInputState.ValidCardCredentialsState -> {
                    binding.progressBarCheckOut.visibility = View.VISIBLE
                    generateToken()
                }
            }
        }

    }

    private fun generateToken() {
        try {
            mCheckoutAPIClient.generateToken(
                CardTokenisationRequest(
                    proceedToCheckoutviewModel.cardNumber.value,
                    "Chintan Soni",
                    CardUtil.returnExpiryDateOfMonthYear(proceedToCheckoutviewModel.cardExpiry.value)
                        ?.get(0),
                    CardUtil.returnExpiryDateOfMonthYear(proceedToCheckoutviewModel.cardExpiry.value)
                        ?.get(1),
                    proceedToCheckoutviewModel.cardCVV.value
                )
            )
        } catch (e: Exception) {
            binding.progressBarCheckOut.visibility = View.GONE
        }
    }

    /**
     * detecting card type on UI, when user typing card number
     */
    private fun checkCardType(value: String) {
        if (value.length > 2 && CardUtil.getCardType(value).imgCardDrawbleId > 0) {
            binding.textInputCardNumber.startIconDrawable =
                ContextCompat.getDrawable(
                    requireContext(),
                    CardUtil.getCardType(value).imgCardDrawbleId
                )
        } else if (value.length < 2) {
            binding.textInputCardNumber.startIconDrawable = null
        }

    }

    // setup binding null on destroying view to avoid leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val mTokenListener: OnTokenGenerated by lazy {
        object : OnTokenGenerated {
        override fun onTokenGenerated(token: CardTokenisationResponse) {
            binding.progressBarCheckOut.visibility = View.GONE
            displayMessage(resources.getString(R.string.token_generated), token.token)
        }

        override fun onError(error: CardTokenisationFail) {
            binding.progressBarCheckOut.visibility = View.GONE
            displayMessage(resources.getString(R.string.generic_error), error.errorType)
        }

        override fun onNetworkError(error: NetworkError) {
            binding.progressBarCheckOut.visibility = View.GONE
            displayMessage(resources.getString(R.string.network_error), error?.localizedMessage!!)
        }
    }
    }

    private fun displayMessage(title: String, message: String) {
        requireContext().alert {
            setTitle(title)
            setMessage(message)
            neutralButton {clearAllFields() }
        }

    }

    private fun clearAllFields() {
        binding.etCardCvv.setText("")
        binding.etCardNumber.setText("")
        binding.etExpiryDate.setText("")
    }
}