package com.checkoutcom.checkoutpractical.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.checkoutcom.checkoutpractical.R
import com.checkoutcom.checkoutpractical.databinding.FragmentSucessFailureBinding
import com.checkoutcom.checkoutpractical.ui.viewmodels.PaymentConclusionViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * PaymentConclusionFragment showing final payment output
 */
@AndroidEntryPoint
class PaymentConclusionFragment : Fragment() {

    private var _binding: FragmentSucessFailureBinding? = null

    private val binding get() = _binding!!

    private val paymentConclusionViewModel: PaymentConclusionViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSucessFailureBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // received fragment arguments (confirmation message from 3dSecureScreen)
        arguments?.let { bundle ->
            binding.paymentMessage = PaymentConclusionFragmentArgs.fromBundle(bundle).paymentMessage
        }
        setUpUI()

    }

    private fun setUpUI() {
        setUpImage()
        paymentConclusionViewModel.storePaymentStatus(getString(R.string.payment_not_started))
    }

    /**
     * setUp Payment confirmation image
     */
    private fun setUpImage() {
        binding.imgPaymentConclusion.setImageDrawable(when {
            binding.paymentMessage.equals(getString(R.string.payment_success_msg)) -> {
                ContextCompat.getDrawable(requireActivity(), R.drawable.ic_success)
            }
            else -> {
                ContextCompat.getDrawable(requireActivity(), R.drawable.ic_fail)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}