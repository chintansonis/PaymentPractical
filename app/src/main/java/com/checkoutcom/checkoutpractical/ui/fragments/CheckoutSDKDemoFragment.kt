package com.checkoutcom.checkoutpractical.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.checkoutcom.checkoutpractical.databinding.FragmentHeadlessDemoBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * CheckoutHeadLessDemoFragment showing final payment output
 */
@AndroidEntryPoint
class CheckoutSDKDemoFragment : Fragment() {

    private var _binding: FragmentHeadlessDemoBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHeadlessDemoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.btnBuy.setOnClickListener {
           findNavController().navigate(CheckoutSDKDemoFragmentDirections.actionCheckoutSDKDemoFragmentToProceedToPayFragment())
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}