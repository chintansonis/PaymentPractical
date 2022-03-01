package com.checkoutcom.checkoutpractical.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.checkoutcom.checkoutpractical.R
import com.checkoutcom.checkoutpractical.databinding.FragmentSucessFailureBinding


/**
 * PaymentConclusionFragment showing final payment output
 */
class PaymentConclusionFragment : Fragment() {

    private var _binding: FragmentSucessFailureBinding? = null

    private val binding get() = _binding!!


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
        binding.btnGotoAddCard.setOnClickListener {
            findNavController().navigate(PaymentConclusionFragmentDirections.actionPaymentConclusionFragmentToProceedToPayFragment())
        }
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