package com.checkoutcom.checkoutpractical.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.checkoutcom.checkoutpractical.databinding.FragmentBuyBtnUiBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * CheckoutHeadLessDemoFragment showing final payment output
 */
@AndroidEntryPoint
class CheckoutSDKDemoFragment : Fragment() {

    private var _binding: FragmentBuyBtnUiBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBuyBtnUiBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //test commit
       binding.btnBuy.setOnClickListener {
           findNavController().navigate(CheckoutSDKDemoFragmentDirections.actionCheckoutSDKDemoFragmentToCheckoutSDKUIDemoFragment())
       }
    }

}